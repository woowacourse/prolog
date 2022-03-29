CREATE TABLE most_popular_studylog
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    studylog_id BIGINT         NOT NULL,
    DELETED     TINYINT(1) DEFAULT 0 NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB;
