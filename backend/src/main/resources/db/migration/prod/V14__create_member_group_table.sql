CREATE TABLE member_group
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    name        VARCHAR(50)  NOT NULL,
    description VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE group_member
(
    id        BIGINT NOT NULL AUTO_INCREMENT,
    member_id BIGINT NOT NULL,
    group_id  BIGINT NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

ALTER TABLE group_member
    ADD CONSTRAINT FK_GROUP_MEMBER_ON_MEMBER FOREIGN KEY (member_id) REFERENCES member (id);

ALTER TABLE group_member
    ADD CONSTRAINT FK_GROUP_MEMBER_ON_MEMBER_GROUP FOREIGN KEY (group_id) REFERENCES member_group (id);
