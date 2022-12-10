CREATE TABLE quiz
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    keyword_id BIGINT       NOT NULL,
    question   VARCHAR(255) NOT NULL,

    CONSTRAINT pk_quiz PRIMARY KEY (id)
) ENGINE = InnoDB;


ALTER TABLE quiz
    ADD CONSTRAINT FK_quiz_keyword_id
        FOREIGN KEY (keyword_id) REFERENCES keyword (id);
