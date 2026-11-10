-- VALIDATION SCRIPT (SQL Server)
-- Fill in the placeholders and run BEFORE applying changes and AFTER applying changes.

-- 1) Capture statistics IO/TIME and XML actual plan
SET STATISTICS IO ON;
SET STATISTICS TIME ON;
SET STATISTICS XML ON;

-- Run the query with representative parameter values
-- Example: DECLARE @p1 INT = 123; EXEC sp_executesql N'<your query here>', N'@p1 INT', @p1;

<YOUR QUERY WITH PARAMETERS HERE>

SET STATISTICS IO OFF;
SET STATISTICS TIME OFF;
SET STATISTICS XML OFF;

-- 2) Collect index usage and table size info
SELECT OBJECT_NAME(s.object_id) AS TableName, i.name AS IndexName, user_seeks, user_scans, user_lookups, user_updates
FROM sys.dm_db_index_usage_stats s
JOIN sys.indexes i ON s.object_id = i.object_id AND s.index_id = i.index_id
WHERE OBJECT_NAME(s.object_id) IN ('YourTable1','YourTable2');

-- 3) Collect rowcounts and sizes
SELECT schema_name(o.schema_id) AS SchemaName, o.name AS TableName,
       p.rows AS RowCount,
       SUM(a.total_pages) * 8 * 1024 AS TotalBytes
FROM sys.objects o
JOIN sys.partitions p ON p.object_id = o.object_id AND p.index_id IN (0,1)
JOIN sys.allocation_units a ON a.container_id = p.partition_id
WHERE o.name IN ('YourTable1','YourTable2')
GROUP BY o.schema_id, o.name, p.rows;

-- 4) Optional: capture plan XML from cache after running (if needed)
-- SELECT qs.sql_handle, qp.query_plan
-- FROM sys.dm_exec_query_stats qs
-- CROSS APPLY sys.dm_exec_query_plan(qs.plan_handle) qp
-- JOIN sys.dm_exec_sql_text(qs.sql_handle) st ON 1=1
-- WHERE st.text LIKE '%some unique fragment%';

-- Save outputs to files for BEFORE/AFTER comparison.
