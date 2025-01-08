create table if not exists prolog.article_like
(
    id
    bigint
    auto_increment
    primary
    key,
    article_id
    bigint
    not
    null,
    member_id
    bigint
    not
    null,
    foreign
    key
(
    member_id
) references prolog.member
(
    id
),
    foreign key
(
    article_id
) references prolog.article
(
    id
)
    ) ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;
