CREATE TABLE studylog_temp (
    id          BIGINT NOT NULL AUTO_INCREMENT,
    member_id   BIGINT NOT NULL,
    title       VARCHAR(255),
    content     VARCHAR(255),
    mission_id  BIGINT,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

alter table studylog_temp
    add constraint FK_STUDYLOG_TEMP_MEMBER
        foreign key (member_id)
            references member (id) on delete cascade;

alter table studylog_temp
    add constraint FK_STUDYLOG_TEMP_MISSION
        foreign key (mission_id)
            references mission (id) on delete cascade;