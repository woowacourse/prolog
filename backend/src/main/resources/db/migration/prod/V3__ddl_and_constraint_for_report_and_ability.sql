create table ability
(
    id          bigint not null auto_increment,
    color       varchar(255),
    description varchar(255),
    name        varchar(255),
    member_id   bigint,
    parent_id   bigint,
    primary key (id)
) engine=InnoDB;

create table ability_graph
(
    id        bigint not null auto_increment,
    report_id bigint,
    primary key (id)
) engine=InnoDB;

create table ability_relationship
(
    id        bigint not null auto_increment,
    source_id bigint,
    target_id bigint,
    primary key (id)
) engine=InnoDB;

create table graph_ability
(
    id               bigint not null auto_increment,
    is_present       bit,
    weight           bigint not null,
    ability_id       bigint not null,
    ability_graph_id bigint not null,
    primary key (id)
) engine=InnoDB;

create table report
(
    id           bigint not null auto_increment,
    description  varchar(255),
    is_represent bit,
    title        varchar(255),
    graph_id     bigint not null,
    member_id    bigint not null,
    primary key (id)
) engine=InnoDB;

create table reported_studylog
(
    id          bigint not null auto_increment,
    studylog_id bigint,
    report_id   bigint not null,
    primary key (id)
) engine=InnoDB;

create table reported_studylog_ability
(
    id                   bigint not null auto_increment,
    ability_id           bigint not null,
    reported_studylog_id bigint not null,
    primary key (id)
) engine=InnoDB;

alter table ability
    add constraint FK_ABILITY_MEMBER
        foreign key (member_id)
            references member (id);

alter table ability
    add constraint FK_ABILITY_PARENT
        foreign key (parent_id)
            references ability (id);

alter table ability_graph
    add constraint FK_ABILITY_GRAPH_REPORT
        foreign key (report_id)
            references report (id);

alter table ability_relationship
    add constraint FK_ABILITY_RELATIONSHIP_SOURCE
        foreign key (source_id)
            references ability (id);

alter table ability_relationship
    add constraint FK_ABILITY_RELATIONSHIP_TARGET
        foreign key (target_id)
            references ability (id);

alter table graph_ability
    add constraint FK_GRAPH_ABILITY_ABILITY
        foreign key (ability_id)
            references ability (id);

alter table graph_ability
    add constraint FK_GRAPH_ABILITY_ABILITY_GRAPH
        foreign key (ability_graph_id)
            references ability_graph (id);

alter table report
    add constraint FK_REPORT_ABILITY_GRAPH
        foreign key (graph_id)
            references ability_graph (id);

alter table report
    add constraint FK_REPORT_MEMBER
        foreign key (member_id)
            references member (id);

alter table reported_studylog
    add constraint FK_REPORTED_STUDYLOG_REPORT
        foreign key (report_id)
            references report (id);

alter table reported_studylog_ability
    add constraint FK_REPORTED_STUDYLOG_ABILITY_ABILITY
        foreign key (ability_id)
            references ability (id);

alter table reported_studylog_ability
    add constraint FK_REPORTED_STUDYLOG_ABILITY_REPORTED_STUDYLOG
        foreign key (reported_studylog_id)
            references reported_studylog (id);
