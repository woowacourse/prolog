CREATE TABLE report_ability (
    id   BIGINT AUTO_INCREMENT NOT NULL,
    color VARCHAR(255),
    description VARCHAR(255),
    name VARCHAR(255),
    origin_ability_id BIGINT,
    weight INTEGER ,
    parent_id BIGINT,
    report_id BIGINT,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

create table report_studylog (
    id   BIGINT AUTO_INCREMENT NOT NULL,
    report_id BIGINT,
    report_ability_id BIGINT,
    studylog_id BIGINT not null,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

create table studylog_ability (
    id   BIGINT AUTO_INCREMENT NOT NULL,
    member_id BIGINT not null,
    ability_id BIGINT not null,
    studylog_id BIGINT not null,
    PRIMARY KEY (id)
) ENGINE=InnoDB;