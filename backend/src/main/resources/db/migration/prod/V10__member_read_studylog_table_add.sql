create table studylog_read (
                                id bigint not null auto_increment,
                                member_id bigint not null,
                                studylog_id bigint not null,
                                primary key (id)
) engine=InnoDB;

alter table studylog_read
    add constraint FK_STUDYLOG_READ_MEMBER
        foreign key (member_id)
            references member (id) on delete cascade;

alter table studylog_read
    add constraint FK_STUDYLOG_READ_STUDYLOG
        foreign key (studylog_id)
            references studylog (id) on delete cascade;
