Query Optimization Playbook — quick README

Files created
- `QUERY_OPTIMIZATION_PLAYBOOK_SQLServer_Oracle.md` — The full playbook with templates, engine-specific commands, workflow, validation, rollback guidance, and examples.
- `QUERY_OPT_REQ_SQLServer.txt` — A ready-to-fill request template for SQL Server tuning tickets.
- `QUERY_OPT_REQ_Oracle.txt` — A ready-to-fill request template for Oracle tuning tickets.
- `VALIDATION_SCRIPT_SQLServer.sql` — A script template to capture BEFORE/AFTER metrics on SQL Server (STATISTICS IO/TIME/XML and index stats).
- `VALIDATION_SCRIPT_Oracle.sql` — A script template to capture BEFORE/AFTER metrics on Oracle (DBMS_XPLAN.DISPLAY_CURSOR and v$sql stats).

Where to find them
- Path: `/Users/sarveshojha/Downloads/demo/`

How to use (quick start)
1. Prepare your attachments
   - Fill `QUERY_OPT_REQ_SQLServer.txt` or `QUERY_OPT_REQ_Oracle.txt` depending on your engine.
   - Run the engine-specific diagnostic commands listed in the playbook and save outputs (actual plan, DDL, indexes, row counts).

2. Run BEFORE validation (SQL Server)
- Open `VALIDATION_SCRIPT_SQLServer.sql`, fill placeholders (`<YOUR QUERY WITH PARAMETERS HERE>`, table names), then run in SSMS or sqlcmd. Example (sqlcmd):

```bash
sqlcmd -S your_server -d your_db -i VALIDATION_SCRIPT_SQLServer.sql -o explain_before_sqlserver.txt
```

3. Run BEFORE validation (Oracle)
- Open `VALIDATION_SCRIPT_Oracle.sql`, fill placeholders (query fragment or bind values). Run in SQL*Plus or SQLcl. Example (SQLcl):

```bash
sql /nolog
CONNECT user/password@service
@VALIDATION_SCRIPT_Oracle.sql > explain_before_oracle.txt
```

4. Submit the request
- Attach the filled request template and the saved diagnostics (plan files, DDL, sizes) to your ticket assigned to the tuner/DBA team.

5. Stage & apply
- Tuner returns recommended changes. Apply them in staging first using the validation scripts to capture AFTER metrics.
- If staging results are good, schedule production deployment and use ONLINE index creation options when available.

6. Compare BEFORE vs AFTER
- Compare wall-time, logical reads / buffer_gets, physical reads, temp-spill indicators, and plan shape.
- Use the exact validation commands in the playbook for side-by-side comparison.

Rollbacks
- Each tuner deliverable must include an exact rollback `DROP INDEX` or `REVERT` command. Keep a `ROLLBACK.sql` ready and tested on staging before production change.

Need anything else?
- I can generate PDF copies of the playbook and the request templates for your Confluence or internal docs.
- I can also pre-fill the templates with your organization's schema naming convention or generate a `ROLLBACK.sql` sample.

If you'd like, I will now generate the PDF versions or pre-filled templates (choose: `PDF`, `pre-fill` with your naming scheme, or `none`).
