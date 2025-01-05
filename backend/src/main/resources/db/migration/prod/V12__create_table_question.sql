create table if not exists prolog.question (
    id   bigint auto_increment primary key,
    content varchar(255),
    mission_id bigint not null,
    foreign key (mission_id) references prolog.mission (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create table if not exists prolog.answer (
    id   bigint auto_increment primary key,
    content varchar(255),
    studylog_id bigint not null,
    question_id bigint not null,
    member_id bigint not null,
    foreign key (studylog_id) references prolog.studylog (id),
    foreign key (question_id) references prolog.question (id),
    foreign key (member_id) references prolog.member (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create table if not exists prolog.answer_temp (
    id   bigint auto_increment primary key,
    content varchar(255),
    studylog_temp_id bigint not null,
    question_id bigint not null,
    member_id bigint not null,
    foreign key (studylog_temp_id) references prolog.studylog_temp (id),
    foreign key (question_id) references prolog.question (id),
    foreign key (member_id) references prolog.member (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
