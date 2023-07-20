drop table prolog.keyword_reference;

create table if not exists prolog.recommended_post
(
    id         bigint auto_increment primary key,
    url        varchar(255) not null,
    keyword_id bigint       not null,
    constraint FK_KEYWORD_ID
        foreign key (keyword_id) references prolog.keyword (id)
);
