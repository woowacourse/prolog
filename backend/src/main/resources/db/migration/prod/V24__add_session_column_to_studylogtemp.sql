ALTER TABLE studylog_temp ADD COLUMN session_id BIGINT;

alter table studylog_temp
    add constraint FK_STUDYLOG_TEMP_SESSION
        foreign key (session_id)
            references session (id) on delete cascade;