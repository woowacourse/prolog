ALTER TABLE mission DROP FOREIGN KEY FK_MISSION_ON_LEVEL;
ALTER TABLE mission ADD CONSTRAINT FK_MISSION_ON_SESSION FOREIGN KEY (session_id) REFERENCES session (id);