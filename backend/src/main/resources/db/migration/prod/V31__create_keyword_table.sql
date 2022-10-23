CREATE TABLE keyword
(
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    seq         BIGINT       NOT NULL,
    importance  BIGINT       NOT NULL,
    session_id  BIGINT       NOT NULL,
    parent_id   BIGINT       NOT NULL,
    CONSTRAINT pk_keyword PRIMARY KEY (id)
) ENGINE = InnoDB;


ALTER TABLE keyword
    ADD CONSTRAINT FK_keyword_session_id
        FOREIGN KEY (session_id) REFERENCES session (id);


ALTER TABLE keyword
    ADD CONSTRAINT FK_keyword_parent_id
        FOREIGN KEY (parent_id) REFERENCES keyword (id);
