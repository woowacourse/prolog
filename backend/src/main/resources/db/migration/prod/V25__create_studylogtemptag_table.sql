CREATE TABLE studylog_temp_tag(
    id               BIGINT NOT NULL AUTO_INCREMENT,
    tag_id           BIGINT NOT NULL,
    studylog_temp_id BIGINT NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

alter table studylog_temp_tag
    add constraint FK_STUDYLOG_TEMP_TAG_TAG
        foreign key (tag_id)
            references tag (id) on delete cascade;

alter table studylog_temp_tag
    add constraint FK_STUDYLOG_TEMP_TAG_STUDYLOG_TEMP
        foreign key (studylog_temp_id)
            references studylog_temp (id) on delete cascade;
