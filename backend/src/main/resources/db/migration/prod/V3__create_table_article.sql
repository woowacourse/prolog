create table if not exists prolog.article
(
    id        bigint auto_increment primary key,
    member_id bigint        not null,
    title     varchar(50)   not null,
    url       varchar(1024) not null,
    created_at datetime(6) not null,
    foreign key (member_id) references prolog.member (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
