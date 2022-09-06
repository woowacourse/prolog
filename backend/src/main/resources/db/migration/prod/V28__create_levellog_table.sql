CREATE TABLE level_log(
    id        BIGINT      NOT NULL AUTO_INCREMENT,
    member_id BIGINT      NOT NULL,
    title     VARCHAR(50) NOT NULL,
    content   TEXT(255) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

ALTER TABLE level_log
    ADD CONSTRAINT FK_LEVEL_LOG_MEMBER
        FOREIGN KEY (member_id) REFERENCES member (id);
