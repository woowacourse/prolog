CREATE TABLE likes
(
    id          BIGINT NOT NULL AUTO_INCREMENT,
    member_id   BIGINT NOT NULL,
    studylog_id BIGINT NOT NULL
    PRIMARY KEY (id)
) ENGINE=InnoDB;

ALTER TABLE likes
    ADD CONSTRAINT FK_STUDYLOG_ON_POST FOREIGN KEY (studylog_id) REFERENCES studylog (id);
