create table if not exists prolog.organization
(
    id   bigint auto_increment primary key,
    name varchar(255) not null
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create table if not exists prolog.organization_group
(
    id              bigint auto_increment primary key,
    organization_id bigint       not null,
    name            varchar(255) not null
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create table if not exists prolog.organization_group_member
(
    id                    bigint auto_increment primary key,
    organization_group_id bigint       not null,
    username              varchar(255) not null,
    nickname              varchar(255) not null,
    member_id             bigint
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create table if not exists prolog.organization_group_session
(
    id                    bigint auto_increment primary key,
    organization_group_id bigint not null,
    session_id            bigint not null
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
