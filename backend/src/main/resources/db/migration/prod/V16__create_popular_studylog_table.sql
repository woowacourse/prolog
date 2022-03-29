CREATE TABLE popular_studylog
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    studylog_id BIGINT         NOT NULL,
    deleted     TINYINT(1) DEFAULT 0 NOT NULL,
    created_at  datetime    NOT NULL,
    updated_at  datetime    NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB;
