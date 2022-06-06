ALTER TABLE report
    ADD COLUMN start_date date;
ALTER TABLE report
    ADD COLUMN end_date date;
ALTER TABLE report DROP is_represent;
ALTER TABLE report DROP CONSTRAINT FK_REPORT_ABILITY_GRAPH;
ALTER TABLE report DROP graph_id;
