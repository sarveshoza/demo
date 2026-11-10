-- VALIDATION SCRIPT (Oracle)
-- Run BEFORE applying changes and AFTER applying changes using the same bind values.

-- 1) Run the query with bind values so cursor is in shared pool (example uses SQL*Plus syntax)
-- VARIABLE b1 NUMBER
-- EXEC :b1 := 123;
-- SELECT * FROM your_table WHERE id = :b1;

-- 2) Capture last executed plan and stats
SELECT * FROM TABLE(DBMS_XPLAN.DISPLAY_CURSOR(NULL, NULL, 'ALLSTATS LAST'));

-- 3) Capture v$sql metrics for logical IO and disk reads
SELECT sql_id, executions, elapsed_time, buffer_gets, disk_reads
FROM v$sql
WHERE sql_text LIKE '%<unique fragment of your query>%';

-- 4) Gather stats if needed after structural changes
-- BEGIN
--   DBMS_STATS.GATHER_TABLE_STATS('YOUR_SCHEMA','YOUR_TABLE', CASCADE => TRUE);
-- END;
-- /

-- Save outputs for BEFORE/AFTER comparison.
