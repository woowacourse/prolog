CREATE TABLE comment(
    id          BIGINT NOT NULL AUTO_INCREMENT,
    member_id   BIGINT NOT NULL,
    studylog_id BIGINT NOT NULL,
    content     TEXT(255) NOT NULL,
    is_delete   TINYINT(1) DEFAULT 0 NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

ALTER TABLE comment
    ADD CONSTRAINT FK_COMMENT_MEMBER
        FOREIGN KEY (member_id) REFERENCES member (id);

ALTER TABLE comment
    ADD CONSTRAINT FK_COMMENT_STUDYLOG
        FOREIGN KEY (studylog_id) REFERENCES studylog (id);
