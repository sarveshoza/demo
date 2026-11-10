Query Optimization Playbook — SQL Server & Oracle

Purpose
- A concise, repeatable playbook teams can use to request, run, validate, and roll back SQL tuning changes for SQL Server and Oracle.
- Includes exact commands to collect diagnostics, required request template, responder output format, validation steps, rollback and monitoring guidance.

High-level plan (what this document gives you)
1. A one-page Request Template to submit a tuning request.
2. Engine-specific diagnostic commands (SQL Server and Oracle) to run and attach.
3. Exact responder output format the tuner must deliver.
4. A safe step-by-step workflow: capture -> propose -> stage -> validate -> deploy -> monitor -> rollback.
5. Quick examples and an appendix of frequently used commands.

Quick checklist (do this before asking for tuning)
- [ ] Fill the Request Template fully (SQL + workload + constraints).
- [ ] Attach CREATE TABLE / column lists and all indexes for tables used.
- [ ] Capture and attach the actual execution plan (SQL Server: Actual Execution Plan / SET STATISTICS IO/TIME/XML; Oracle: DBMS_XPLAN.DISPLAY_CURSOR 'ALLSTATS LAST').
- [ ] Capture table row counts/sizes and index usage stats.
- [ ] Provide representative parameter values for parameterized queries.
- [ ] Note environment and change constraints (can we create indexes? online only?).

Request Template (paste & fill)
ORIGINAL SQL:
[Paste full SQL here — include literal or representative parameter values]

WORKLOAD
- Type: [ad-hoc / OLTP / reporting / batch]
- Frequency / concurrency: [e.g., 1 qps, 200 concurrent users]
- SLA / Target latency: [e.g., reduce 12s -> <1s]

ENVIRONMENT & CONSTRAINTS
- DBMS & version: [SQL Server 2019 / Oracle 19c etc.]
- Single node or managed? [on-prem / Azure / OCI]
- Constraints: [no schema changes in prod / indexes must be ONLINE / maintenance window ...]

ATTACH (required!)
- CREATE TABLE DDL for all tables involved (or detailed column/type list including PK/FK).
- Current indexes (names, key columns, included columns, filter or function-based expressions).
- Execution plan with actual metrics (see engine-specific commands below).
- Table row counts and sizes.
- Representative parameter values.

Required responder output format (tuner MUST follow exactly)
1) Executive summary (1–2 lines): expected improvement & main tactic.
2) Problem areas (1–3 bullets): root causes (missing index, seq scan/large scan, large sort, bad cardinality, tempdb spills, etc.).
3) Proposed changes — numbered and actionable:
   a) Index DDL (exact T-SQL / Oracle SQL). State ONLINE/CONCURRENT options and estimated build time/impact.
   b) Full optimized SQL (complete statement) — minimal changes preferred.
   c) Join order / strategy guidance and why (based on plan cardinalities).
   d) Stats / recompile / parameter sniff mitigation commands (exact commands/hints).
   e) Physical design suggestions (partitioning, materialized view) with migration steps and refresh strategy.
   f) Planner / engine config tuning (exact settings & rationale, safe example values).
4) Risks & rollback (exact DROP/ALTER commands and safety notes).
5) Validation steps (exact BEFORE/AFTER commands to run and metrics to compare).
6) Deliverables checklist (index DDL, rewritten SQL, validation script, monitoring queries).

Step-by-step workflow (detailed)
1. Capture current state (Before):
   - Fill Request Template and gather attachments.
   - Save these artifacts: `QUERY_OPT_REQ.txt`, `DDL_AND_INDEXES.txt`, `EXPLAIN_BEFORE.txt` (or JSON/XML).
   - In production, take note of allowed maintenance window and whether ONLINE index operations are required.

2. Submit request to the tuner with attachments.

3. Tuner analysis & proposal
   - Tuner returns best-effort changes in the Required responder format.
   - If the tuner requests more data, provide rapidly (e.g., table sample rows, histograms).

4. Stage changes in a testing/staging environment that mirrors production data distribution.
   - Create indexes with ONLINE options when available.
   - For Oracle: use `ONLINE` clause where supported; for SQL Server: `WITH (ONLINE = ON)` when your edition supports it.

5. Validate in staging (exact commands below) using the same parameter values.
   - Capture BEFORE and AFTER plans and metrics.
   - Confirm wall-time, logical reads / buffer gets, temp-spill usage, and that query shape changed as expected.

6. Deploy to production (if staging validated)
   - Schedule during allowed window or run ONLINE index creation.
   - Monitor for locking, write-performance impact, and replication lag if applicable.

7. Monitor & regressions
   - Continuously observe query performance and top offender lists (DMVs / AWR / ASH / Query Store).
   - Rollback promptly if regressions occur using the exact DROP commands provided.

Engine-specific diagnostics & commands

A — SQL Server (T-SQL snippets & guidance)

Collect schema & DDL
```sql
-- Columns & types
SELECT TABLE_SCHEMA, TABLE_NAME, COLUMN_NAME, DATA_TYPE, CHARACTER_MAXIMUM_LENGTH
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_NAME IN ('YourTable1','YourTable2');

-- Table constraints
SELECT * FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE TABLE_NAME IN ('YourTable1','YourTable2');
```

Collect index definitions & included columns
```sql
SELECT
  s.name AS SchemaName,
  t.name AS TableName,
  i.name AS IndexName,
  i.type_desc,
  i.is_unique,
  i.is_primary_key,
  STUFF((SELECT ',' + c.name
         FROM sys.index_columns ic JOIN sys.columns c
           ON ic.object_id = c.object_id AND ic.column_id = c.column_id
         WHERE ic.object_id = i.object_id AND ic.index_id = i.index_id AND ic.is_included_column = 0
         ORDER BY ic.key_ordinal
         FOR XML PATH('')),1,1,'') AS KeyColumns,
  STUFF((SELECT ',' + c.name
         FROM sys.index_columns ic JOIN sys.columns c
           ON ic.object_id = c.object_id AND ic.column_id = c.column_id
         WHERE ic.object_id = i.object_id AND ic.index_id = i.index_id AND ic.is_included_column = 1
         FOR XML PATH('')),1,1,'') AS IncludedColumns
FROM sys.indexes i
JOIN sys.tables t ON i.object_id = t.object_id
JOIN sys.schemas s ON t.schema_id = s.schema_id
WHERE t.name IN ('YourTable1','YourTable2') AND i.index_id > 0;
```

Table sizes & rowcounts
```sql
SELECT schema_name(o.schema_id) AS SchemaName, o.name AS TableName,
       p.rows AS RowCount,
       SUM(a.total_pages) * 8 * 1024 AS TotalBytes
FROM sys.objects o
JOIN sys.partitions p ON p.object_id = o.object_id AND p.index_id IN (0,1)
JOIN sys.allocation_units a ON a.container_id = p.partition_id
WHERE o.name IN ('YourTable1','YourTable2')
GROUP BY o.schema_id, o.name, p.rows;
```

Capture actual plan & IO/time
- In SSMS: enable "Include Actual Execution Plan", or run:
```sql
SET STATISTICS IO ON;
SET STATISTICS TIME ON;
SET STATISTICS XML ON;
-- Run the query with exact parameters
<your query here>;
SET STATISTICS IO OFF;
SET STATISTICS TIME OFF;
SET STATISTICS XML OFF;
```
- Save the XML plan (right-click plan -> Save Execution Plan As...) or retrieve from `sys.dm_exec_query_stats` / `sys.dm_exec_query_plan`.

Index usage and fragmentation diagnostics
```sql
-- index usage stats
SELECT OBJECT_NAME(s.object_id) AS TableName, i.name AS IndexName, user_seeks, user_scans, user_lookups, user_updates
FROM sys.dm_db_index_usage_stats s
JOIN sys.indexes i ON s.object_id = i.object_id AND s.index_id = i.index_id
WHERE OBJECT_NAME(s.object_id) IN ('YourTable1','YourTable2');

-- fragmentation
SELECT OBJECT_NAME(p.object_id) AS TableName, i.name AS IndexName, avg_fragmentation_in_percent, page_count
FROM sys.dm_db_index_physical_stats(DB_ID(), NULL, NULL, NULL, 'LIMITED') p
JOIN sys.indexes i ON p.object_id = i.object_id AND p.index_id = i.index_id
WHERE OBJECT_NAME(p.object_id) IN ('YourTable1','YourTable2');
```

SQL Server-specific tuning hints & commands to consider
- Create index online (if supported):
```sql
CREATE NONCLUSTERED INDEX IX_name ON schema.table(col1, col2) INCLUDE(col3) WITH (ONLINE = ON);
```
- If parameter sniffing causes bad plans: consider `OPTION (RECOMPILE)`, `OPTIMIZE FOR (@p = value)`, or plan guides.
- Check tempdb spills in the plan XML (look for "Spill to Tempdb" or excessive memory grant warnings).

Validation (SQL Server) — exact BEFORE/AFTER commands
```sql
-- BEFORE
SET STATISTICS IO ON; SET STATISTICS TIME ON; SET STATISTICS XML ON;
<your query with parameters>;
SET STATISTICS IO OFF; SET STATISTICS TIME OFF; SET STATISTICS XML OFF;
-- Save the XML and IO/TIME outputs.

-- AFTER (same commands, same parameter values)
SET STATISTICS IO ON; SET STATISTICS TIME ON; SET STATISTICS XML ON;
<your query with same parameters>;
SET STATISTICS IO OFF; SET STATISTICS TIME OFF; SET STATISTICS XML OFF;
```

Rollback example (SQL Server)
```sql
DROP INDEX IX_name ON schema.table;
```

Monitoring post-deploy (SQL Server)
- Use Query Store (if enabled) to compare plan/runtime changes.
- Monitor `sys.dm_db_index_usage_stats` for index reads/writes and `sys.dm_exec_query_stats` for regressions.

B — Oracle (SQL & PL/SQL snippets & guidance)

Collect schema / DDL
```sql
-- Full DDL
SELECT DBMS_METADATA.GET_DDL('TABLE','YOUR_TABLE','YOUR_SCHEMA') FROM DUAL;

-- Column list
SELECT owner, table_name, column_name, data_type, data_length
FROM all_tab_columns
WHERE table_name IN ('YOUR_TABLE1','YOUR_TABLE2') AND owner = 'YOUR_SCHEMA';
```

Collect index list & columns
```sql
SELECT owner, index_name, index_type, uniqueness
FROM all_indexes
WHERE table_name IN ('YOUR_TABLE1','YOUR_TABLE2') AND owner = 'YOUR_SCHEMA';

SELECT index_name, column_name, column_position
FROM all_ind_columns
WHERE table_name = 'YOUR_TABLE' AND index_owner = 'YOUR_SCHEMA'
ORDER BY index_name, column_position;
```

Table stats and sizes
```sql
SELECT table_name, num_rows, blocks, last_analyzed
FROM all_tables
WHERE table_name IN ('YOUR_TABLE1','YOUR_TABLE2') AND owner = 'YOUR_SCHEMA';
```

Capture actual plan & runtime metrics
1. Run the query with bind values (so the cursor is in the shared pool).
2. Run:
```sql
SELECT * FROM TABLE(DBMS_XPLAN.DISPLAY_CURSOR(NULL, NULL, 'ALLSTATS LAST'));
```
- Alternatively, use `EXPLAIN PLAN FOR` prior to execution and show `DBMS_XPLAN.DISPLAY`.

Gather and tune statistics
```sql
BEGIN
  DBMS_STATS.GATHER_TABLE_STATS('YOUR_SCHEMA','YOUR_TABLE', CASCADE => TRUE, GRANULARITY => 'AUTO');
END;
/

-- To gather more detailed column stats if skewed
BEGIN
  DBMS_STATS.GATHER_TABLE_STATS('YOUR_SCHEMA','YOUR_TABLE', METHOD_OPT => 'FOR COLUMNS SIZE 254 YOUR_COLUMN');
END;
/
```

Oracle-specific tuning & index commands
```sql
-- standard index
CREATE INDEX IX_name ON YOUR_SCHEMA.TABLE (COL1, COL2) ONLINE;

-- function-based index example
CREATE INDEX IX_LOWER_EMAIL ON YOUR_SCHEMA.USERS (LOWER(EMAIL));

-- drop index
DROP INDEX YOUR_SCHEMA.IX_name;
```

Validation (Oracle) — BEFORE/AFTER
```sql
-- BEFORE: run the query with bind values
-- then capture plan
SELECT * FROM TABLE(DBMS_XPLAN.DISPLAY_CURSOR(NULL, NULL, 'ALLSTATS LAST'));

-- AFTER: run with same bind values, then re-run DISPLAY_CURSOR and compare:
SELECT * FROM TABLE(DBMS_XPLAN.DISPLAY_CURSOR(NULL, NULL, 'ALLSTATS LAST'));
```

Rollback example (Oracle)
```sql
DROP INDEX YOUR_SCHEMA.IX_name;
BEGIN DBMS_STATS.GATHER_TABLE_STATS('YOUR_SCHEMA','YOUR_TABLE'); END; /
```

Cross-engine best practices (applies to both SQL Server and Oracle)
- Avoid SELECT *; list explicit columns to allow index-only / covering index benefits and smaller network payload.
- Create composite / covering indexes that support WHERE + JOIN + ORDER BY when justified by workload.
- Use filtered/partial indexes (SQL Server supports filtered indexes; in Oracle use function-based indexes or virtual columns to mimic filter predicates when helpful).
- Replace correlated subqueries with joins, or vice versa depending on cost — test both forms.
- For pagination avoid deep OFFSET; prefer keyset pagination.
- Use materialized views (Oracle) or indexed views (SQL Server, with constraints) for repeated expensive aggregations; include refresh strategies and maintenance costs.
- When planner misestimates occur, gather statistics and increase histogram resolution/column stats for skewed distributions.

Validation metrics to compare (exact comparisons to run)
- Wall-clock time (mean, median, p95) for representative parameter set.
- Logical reads / buffer gets (SQL Server: logical_reads via STATISTICS IO; Oracle: buffer_gets & disk_reads from v$sql or DBMS_XPLAN stats).
- Physical reads / disk IO.
- Tempdb or temp-space spills (SQL Server tempdb spills in plan; Oracle temp usage/spills).
- Plan shape (seek/scan, join type changes, sorts, hash operations).
- Throughput under concurrency if relevant.

Risk & rollback guidance
- Always prefer ONLINE index creation where supported in production.
- Keep a two-step rollback: 1) DROP created indexes, 2) Re-gather stats and re-run the query to allow optimizer to re-evaluate.
- Prepare `ROLLBACK.sql` with exact commands before deploying changes.

Appendix: Short cheat-sheet — common commands

SQL Server
- Capture plan & IO/time
```sql
SET STATISTICS IO ON; SET STATISTICS TIME ON; SET STATISTICS XML ON;
<your query>;
SET STATISTICS IO OFF; SET STATISTICS TIME OFF; SET STATISTICS XML OFF;
```
- Create online index
```sql
CREATE NONCLUSTERED INDEX IX_name ON schema.table(col1, col2) INCLUDE (col3) WITH (ONLINE = ON);
```
- Drop index
```sql
DROP INDEX IX_name ON schema.table;
```

Oracle
- Capture actual last execution plan
```sql
SELECT * FROM TABLE(DBMS_XPLAN.DISPLAY_CURSOR(NULL, NULL, 'ALLSTATS LAST'));
```
- Gather table stats
```sql
BEGIN DBMS_STATS.GATHER_TABLE_STATS('YOUR_SCHEMA','YOUR_TABLE', CASCADE => TRUE); END; /
```
- Create index (online where supported)
```sql
CREATE INDEX IX_name ON YOUR_SCHEMA.TABLE (COL1, COL2) ONLINE;
```

Example minimal tuner deliverable (what you should expect)
- Executive summary: "Add covering index on Orders(CustomerID, OrderDate) INCLUDE(TotalAmount) and rewrite subquery to INNER JOIN; expected median latency from ~6s → <0.8s and logical reads reduced by ~85%."
- Index DDL (SQL Server):
```sql
CREATE NONCLUSTERED INDEX IX_Orders_Cust_Date
ON dbo.Orders (CustomerID, OrderDate DESC)
INCLUDE (TotalAmount, Status)
WITH (ONLINE = ON);
```
- Index DDL (Oracle):
```sql
CREATE INDEX IX_ORDERS_CUST_DATE ON YOUR_SCHEMA.ORDERS (CUSTOMER_ID, ORDER_DATE) ONLINE;
```
- Rewritten SQL (example full statement):
```sql
SELECT o.OrderID, o.TotalAmount, c.Name
FROM Orders o
JOIN Customers c ON c.CustomerID = o.CustomerID
WHERE c.Status = 'active' AND o.OrderDate >= DATE '2026-01-01'
ORDER BY o.OrderDate DESC
FETCH FIRST 100 ROWS ONLY;
```
- Validation commands: exact STATISTICS IO/TIME/XML (SQL Server) and DBMS_XPLAN.DISPLAY_CURSOR (Oracle) before/after.
- Rollback: DROP INDEX ... and re-gather stats.

Deliverables you should keep in your tuning ticket
- `QUERY_OPT_REQ.txt` — filled request template.
- `DDL_AND_INDEXES.txt` — CREATE TABLE and index listing.
- `EXPLAIN_BEFORE.*` — raw plan(s) saved (XML/JSON/SQL text).
- `TUNER_RESPONSE.md` — tuner output following Required responder format.
- `VALIDATION_SCRIPT_[SQLServer|Oracle].sql` — exact commands to run pre/post.
- `ROLLBACK.sql` — exact rollback commands.

Next steps / suggestions
- Copy the Request Template into your ticketing system and require attachments before triage.
- Use `QUERY_OPT_REQ.txt` with the attachments when requesting tuner work.
- If you want, I can also create the following placeholder files in the repo to standardize requests:
  - `QUERY_OPT_REQ_SQLServer.txt`
  - `QUERY_OPT_REQ_Oracle.txt`
  - `VALIDATION_SCRIPT_SQLServer.sql`
  - `VALIDATION_SCRIPT_Oracle.sql`

Tell me which placeholder files you'd like me to create (or I can create all 4).
