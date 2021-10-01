CREATE TABLE member_scrap_studylog
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    member_id      BIGINT NOT NULL,
    studylog_id    BIGINT INT NULL,
    CONSTRAINT pk_member_scrap_studylog PRIMARY KEY (id)
);
