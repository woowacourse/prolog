DROP TABLE IF EXISTS essay_answer;
CREATE TABLE essay_answer
(
    id         BIGINT      NOT NULL AUTO_INCREMENT,
    quiz_id    BIGINT      NOT NULL,
    answer     TEXT        NOT NULL,
    member_id  BIGINT      NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,

    CONSTRAINT pk_essay_answer PRIMARY KEY (id)
) ENGINE = InnoDB;
