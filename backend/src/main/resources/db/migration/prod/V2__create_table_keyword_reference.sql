create table if not exists prolog.keyword_reference
(
    keyword_id bigint       not null,
    url        varchar(255) null,
    constraint FK_KEYWORD_ID
        foreign key (keyword_id) references prolog.keyword (id)
);



