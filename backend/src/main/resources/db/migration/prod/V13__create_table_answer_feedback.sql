ALTER TABLE prolog.mission
    ADD COLUMN goal varchar(2048) NOT NULL DEFAULT '';

ALTER TABLE prolog.answer
    MODIFY COLUMN content varchar(1024) NOT NULL DEFAULT '';

create table if not exists prolog.answer_feedback (
    id bigint auto_increment primary key,
    question_id bigint not null,
    member_id bigint not null,
    goal varchar(2048) not null,
    question varchar(1024) not null,
    answer varchar(1024) not null,
    strengths varchar(1024) not null,
    improvement_points varchar(1024) not null,
    additional_learning varchar(1024) not null,
    score int not null,
    foreign key (question_id) references prolog.question (id),
    foreign key (member_id) references prolog.member (id)
) ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;
