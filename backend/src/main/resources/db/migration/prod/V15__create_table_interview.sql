create table if not exists prolog.interview_session (
    id bigint auto_increment primary key,
    created_at datetime not null,
    updated_at datetime not null,
    member_id bigint not null,
    finished boolean not null,

    foreign key (member_id) references prolog.member (id)
) ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


create table if not exists prolog.interview_message (
    created_at datetime not null,
    session_id bigint not null,
    member_id bigint not null,
    sender enum ('INTERVIEWEE','INTERVIEWER','SYSTEM') not null,
    type enum ('CLOSING_SUMMARY','FOLLOW_UP','INITIAL_QUESTION','SYSTEM_GUIDE') not null,
    finished boolean not null,
    formatted_content varchar(8000) not null,
    original_content varchar(8000) not null,
    message_order integer not null,

    primary key (message_order, session_id),
    foreign key (session_id) references prolog.interview_session (id)
) ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;
