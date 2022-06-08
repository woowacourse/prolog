create table studylog_temp_tag (
                               id bigint not null auto_increment,
                               tag_id bigint
                               studylog_temp_id bigint not null,
                               primary key (id)
) engine=InnoDB;

alter table studylog_temp_tag
    add constraint FK_STUDYLOG_TEMP_TAG_TAG
        foreign key (tag_id)
            references tag (id) on delete cascade;

alter table studylog_temp_tag
    add constraint FK_STUDYLOG_TEMP_TAG_STUDYLOG_TEMP
        foreign key (studylog_temp_id)
            references studylog_temp (id) on delete cascade;
