create table if not exists prolog.curriculum
(
    id   bigint auto_increment primary key,
    name varchar(45) not null
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create table if not exists prolog.default_ability
(
    id          bigint auto_increment primary key,
    name        varchar(255) null,
    description varchar(255) null,
    color       varchar(255) null,
    template    varchar(255) null,
    parent_id   bigint       null
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create table if not exists prolog.essay_answer
(
    id         bigint auto_increment primary key,
    quiz_id    bigint      not null,
    answer     text        not null,
    member_id  bigint      not null,
    created_at datetime(6) not null,
    updated_at datetime(6) not null,
    constraint UK_essay_answer_quiz_id_member_id
        unique (quiz_id, member_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create table if not exists prolog.level
(
    id   bigint auto_increment primary key,
    name varchar(45) not null
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create table if not exists prolog.member
(
    id            bigint auto_increment primary key,
    username      varchar(255) not null,
    nickname      varchar(255) null,
    role          varchar(255) null,
    github_id     bigint       not null,
    image_url     varchar(255) not null,
    profile_intro text         null,
    constraint uc_member_githubid
        unique (github_id),
    constraint uc_member_username
        unique (username)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create table if not exists prolog.ability
(
    id          bigint auto_increment primary key,
    color       varchar(255) null,
    description varchar(255) null,
    name        varchar(255) null,
    member_id   bigint       null,
    parent_id   bigint       null,
    constraint FK_ABILITY_MEMBER
        foreign key (member_id) references prolog.member (id),
    constraint FK_ABILITY_PARENT
        foreign key (parent_id) references prolog.ability (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create table if not exists prolog.level_log
(
    id         bigint auto_increment primary key,
    member_id  bigint      not null,
    title      varchar(50) not null,
    content    text        not null,
    created_at datetime    not null,
    updated_at datetime    null,
    constraint FK_LEVEL_LOG_MEMBER
        foreign key (member_id) references prolog.member (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create table if not exists prolog.member_group
(
    id          bigint auto_increment primary key,
    name        varchar(50)  not null,
    description varchar(100) not null
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create table if not exists prolog.group_member
(
    id        bigint auto_increment primary key,
    member_id bigint not null,
    group_id  bigint not null,
    constraint FK_GROUP_MEMBER_ON_MEMBER
        foreign key (member_id) references prolog.member (id),
    constraint FK_GROUP_MEMBER_ON_MEMBER_GROUP
        foreign key (group_id) references prolog.member_group (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create table if not exists prolog.member_tag
(
    id        bigint auto_increment primary key,
    member_id bigint not null,
    tag_id    bigint not null,
    count     int    null
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create table if not exists prolog.popular_studylog
(
    id          bigint auto_increment primary key,
    studylog_id bigint               not null,
    deleted     tinyint(1) default 0 not null,
    created_at  datetime             not null,
    updated_at  datetime             null
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create table if not exists prolog.report
(
    id          bigint auto_increment primary key,
    description text         null,
    title       varchar(255) null,
    member_id   bigint       not null,
    created_at  timestamp    null,
    updated_at  timestamp    null,
    start_date  date         null,
    end_date    date         null,
    constraint FK_REPORT_MEMBER
        foreign key (member_id) references prolog.member (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create table if not exists prolog.ability_graph
(
    id        bigint auto_increment primary key,
    report_id bigint null,
    constraint FK_ABILITY_GRAPH_REPORT
        foreign key (report_id) references prolog.report (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create table if not exists prolog.graph_ability
(
    id               bigint auto_increment primary key,
    is_present       bit    null,
    weight           bigint not null,
    ability_id       bigint not null,
    ability_graph_id bigint not null,
    constraint FK_GRAPH_ABILITY_ABILITY_GRAPH
        foreign key (ability_graph_id) references prolog.ability_graph (id),
    constraint FK_GRAPH_ABILITY_ABILITY_WITH_ON_DELETE_CASCADE
        foreign key (ability_id) references prolog.ability (id)
            on delete cascade
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create table if not exists prolog.report_ability
(
    id                bigint auto_increment primary key,
    color             varchar(255) null,
    description       varchar(255) null,
    name              varchar(255) null,
    origin_ability_id bigint       null,
    weight            int          null,
    parent_id         bigint       null,
    report_id         bigint       null
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create table if not exists prolog.report_studylog
(
    id                            bigint auto_increment primary key,
    report_id                     bigint       null,
    report_ability_id             bigint       null,
    studylog_id                   bigint       not null,
    report_studylog_ability_name  varchar(255) null,
    report_studylog_ability_color varchar(255) null
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create table if not exists prolog.reported_studylog
(
    id          bigint auto_increment primary key,
    studylog_id bigint null,
    report_id   bigint not null,
    constraint FK_REPORTED_STUDYLOG_REPORT
        foreign key (report_id) references prolog.report (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create table if not exists prolog.reported_studylog_ability
(
    id                   bigint auto_increment primary key,
    ability_id           bigint not null,
    reported_studylog_id bigint not null,
    constraint FK_REPORTED_STUDYLOG_ABILITY_ABILITY
        foreign key (ability_id) references prolog.ability (id),
    constraint FK_REPORTED_STUDYLOG_ABILITY_REPORTED_STUDYLOG
        foreign key (reported_studylog_id) references prolog.reported_studylog (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create table if not exists prolog.self_discussion
(
    id           bigint auto_increment primary key,
    level_log_id bigint       not null,
    question     varchar(255) not null,
    answer       text         not null,
    constraint FK_self_discussion_level_log
        foreign key (level_log_id) references prolog.level_log (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create table if not exists prolog.session
(
    id            bigint auto_increment primary key,
    name          varchar(45) not null,
    curriculum_id bigint      null
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create table if not exists prolog.keyword
(
    id          bigint auto_increment primary key,
    name        varchar(255) not null,
    description varchar(255) not null,
    seq         int          not null,
    importance  int          not null,
    session_id  bigint       not null,
    parent_id   bigint       null,
    constraint FK_keyword_parent_id
        foreign key (parent_id) references prolog.keyword (id),
    constraint FK_keyword_session_id
        foreign key (session_id) references prolog.session (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create table if not exists prolog.mission
(
    id         bigint auto_increment primary key,
    name       varchar(45) not null,
    session_id bigint      null,
    constraint FK_MISSION_ON_SESSION
        foreign key (session_id) references prolog.session (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create table if not exists prolog.quiz
(
    id         bigint auto_increment primary key,
    keyword_id bigint       not null,
    question   varchar(255) not null,
    constraint FK_quiz_keyword_id
        foreign key (keyword_id) references prolog.keyword (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create table if not exists prolog.session_member
(
    id         bigint auto_increment primary key,
    member_id  bigint not null,
    session_id bigint not null,
    constraint FK_SESSION_MEMBER_ON_MEMBER
        foreign key (member_id) references prolog.member (id),
    constraint FK_SESSION_MEMBER_ON_SESSION
        foreign key (session_id) references prolog.session (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create table if not exists prolog.studylog
(
    id         bigint auto_increment primary key,
    member_id  bigint                               not null,
    created_at datetime   default CURRENT_TIMESTAMP not null,
    updated_at datetime   default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    title      varchar(50)                          not null,
    content    text                                 not null,
    mission_id bigint                               null,
    views      int        default 0                 null,
    deleted    tinyint(1) default 0                 null,
    session_id bigint                               null,
    constraint FK_POST_ON_MEMBER
        foreign key (member_id) references prolog.member (id),
    constraint FK_POST_ON_MISSION
        foreign key (mission_id) references prolog.mission (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create table if not exists prolog.comment
(
    id          bigint auto_increment primary key,
    member_id   bigint               not null,
    studylog_id bigint               not null,
    content     text                 not null,
    is_delete   tinyint(1) default 0 not null,
    created_at  datetime             not null,
    updated_at  datetime             null,
    constraint FK_COMMENT_MEMBER
        foreign key (member_id) references prolog.member (id),
    constraint FK_COMMENT_STUDYLOG
        foreign key (studylog_id) references prolog.studylog (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create table if not exists prolog.likes
(
    id          bigint auto_increment primary key,
    member_id   bigint not null,
    studylog_id bigint not null,
    constraint FK_STUDYLOG_ON_POST
        foreign key (studylog_id) references prolog.studylog (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create table if not exists prolog.studylog_ability
(
    id          bigint auto_increment primary key,
    member_id   bigint not null,
    ability_id  bigint not null,
    studylog_id bigint not null
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create table if not exists prolog.studylog_read
(
    id          bigint auto_increment primary key,
    member_id   bigint not null,
    studylog_id bigint not null,
    constraint FK_STUDYLOG_READ_MEMBER
        foreign key (member_id) references prolog.member (id)
            on delete cascade,
    constraint FK_STUDYLOG_READ_STUDYLOG
        foreign key (studylog_id) references prolog.studylog (id)
            on delete cascade
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create table if not exists prolog.studylog_scrap
(
    id          bigint auto_increment primary key,
    member_id   bigint not null,
    studylog_id bigint not null,
    constraint FK_STUDYLOG_SCRAP_MEMBER
        foreign key (member_id) references prolog.member (id)
            on delete cascade,
    constraint FK_STUDYLOG_SCRAP_STUDYLOG
        foreign key (studylog_id) references prolog.studylog (id)
            on delete cascade
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create table if not exists prolog.studylog_temp
(
    id         bigint auto_increment primary key,
    member_id  bigint       not null,
    title      varchar(255) null,
    content    varchar(255) null,
    mission_id bigint       null,
    session_id bigint       null,
    constraint FK_STUDYLOG_TEMP_MEMBER
        foreign key (member_id) references prolog.member (id)
            on delete cascade,
    constraint FK_STUDYLOG_TEMP_MISSION
        foreign key (mission_id) references prolog.mission (id)
            on delete cascade,
    constraint FK_STUDYLOG_TEMP_SESSION
        foreign key (session_id) references prolog.session (id)
            on delete cascade
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create table if not exists prolog.studylog_temp_ability
(
    id               bigint auto_increment primary key,
    member_id        bigint not null,
    ability_id       bigint not null,
    studylog_temp_id bigint not null,
    constraint FK_STUDYLOG_TEMP_ABILITY_ABILITY
        foreign key (ability_id) references prolog.ability (id)
            on delete cascade,
    constraint FK_STUDYLOG_TEMP_ABILITY_STUDYLOG_TEMP
        foreign key (studylog_temp_id) references prolog.studylog_temp (id)
            on delete cascade
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create table if not exists prolog.tag
(
    id   bigint auto_increment primary key,
    name varchar(20) not null
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create table if not exists prolog.studylog_tag
(
    id          bigint auto_increment primary key,
    studylog_id bigint null,
    tag_id      bigint not null,
    constraint FK_POST_TAG_ON_POST
        foreign key (studylog_id) references prolog.studylog (id),
    constraint FK_POST_TAG_ON_TAG
        foreign key (tag_id) references prolog.tag (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create table if not exists prolog.studylog_temp_tag
(
    id               bigint auto_increment primary key,
    tag_id           bigint not null,
    studylog_temp_id bigint not null,
    constraint FK_STUDYLOG_TEMP_TAG_STUDYLOG_TEMP
        foreign key (studylog_temp_id) references prolog.studylog_temp (id)
            on delete cascade,
    constraint FK_STUDYLOG_TEMP_TAG_TAG
        foreign key (tag_id) references prolog.tag (id)
            on delete cascade
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create table if not exists prolog.updated_contents
(
    id             bigint auto_increment primary key,
    update_content varchar(255) null,
    updated        int          null
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
