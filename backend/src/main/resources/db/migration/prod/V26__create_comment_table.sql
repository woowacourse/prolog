CREATE TABLE comment
(
    id          BIGINT NOT NULL AUTO_INCREMENT,
    member_id   BIGINT NOT NULL,
    studylog_id BIGINT NOT NULL,
    content     TEXT(255) NOT NULL,
    isDelete    TINYINT(1) DEFAULT 0 NOT NULL
    PRIMARY KEY (id)
) ENGINE=InnoDB;


ALTER TABLE member
    ADD CONSTRAINT FK_comment_MEMBER
        FOREIGN KEY (member_id) REFERENCES member (id);

ALTER TABLE studylog
    ADD CONSTRAINT FK_comment_studylog
        FOREIGN KEY (studylog_id) REFERENCES studylog (id);
