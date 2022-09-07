CREATE TABLE self_discussion
(
    id           BIGINT       NOT NULL AUTO_INCREMENT,
    level_log_id BIGINT       NOT NULL,
    question     VARCHAR(255) NOT NULL,
    answer       TEXT         NOT NULL,
    CONSTRAINT pk_self_discussion PRIMARY KEY (id)
) ENGINE = InnoDB;


ALTER TABLE self_discussion
    ADD CONSTRAINT FK_self_discussion_level_log
        FOREIGN KEY (level_log_id) REFERENCES level_log (id);