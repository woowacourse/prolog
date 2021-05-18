drop table if exists MEMBER;

create table if not exists MEMBER
(
    id bigint auto_increment not null,
    nickname varchar(255) not null,
    role varchar(20) not null,
    github_id bigint not null unique,
    image_url varchar(255) not null,
    primary key(id)
);