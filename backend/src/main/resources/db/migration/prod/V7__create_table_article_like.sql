create table if not exists prolog.article_like
(
    id         bigint auto_increment primary key,
    article_id bigint not null,
    member_id  bigint not null
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
