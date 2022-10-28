CREATE TABLE studylog_temp_ability(
    id               BIGINT NOT NULL AUTO_INCREMENT,
    member_id        BIGINT NOT NULL,
    ability_id      BIGINT NOT NULL,
    studylog_temp_id BIGINT NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

alter table studylog_temp_ability
    add constraint FK_STUDYLOG_TEMP_ABILITY_ABILITY
        foreign key (ability_id)
            references ability (id) on delete cascade;

alter table studylog_temp_ability
    add constraint FK_STUDYLOG_TEMP_ABILITY_STUDYLOG_TEMP
        foreign key (studylog_temp_id)
            references studylog_temp (id) on delete cascade;
