create table studylog_scrap (
                                id bigint not null auto_increment,
                                member_id bigint not null,
                                studylog_id bigint not null,
                                primary key (id)
) engine=InnoDB;

alter table studylog_scrap
    add constraint FK_STUDYLOG_SCRAP_MEMBER
        foreign key (member_id)
            references member (id) on delete cascade;

alter table studylog_scrap
    add constraint FK_STUDYLOG_SCRAP_STUDYLOG
        foreign key (studylog_id)
            references studylog (id) on delete cascade;