CREATE TABLE level
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(45) NOT NULL,
    CONSTRAINT pk_level PRIMARY KEY (id)
);

CREATE TABLE member
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    username  VARCHAR(255) NOT NULL,
    nickname  VARCHAR(255) NULL,
    `role`    VARCHAR(255) NULL,
    github_id BIGINT       NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    CONSTRAINT pk_member PRIMARY KEY (id)
);

CREATE TABLE mission
(
    id       BIGINT AUTO_INCREMENT NOT NULL,
    name     VARCHAR(45) NOT NULL,
    level_id BIGINT NULL,
    CONSTRAINT pk_mission PRIMARY KEY (id)
);

CREATE TABLE post
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    created_at datetime    NOT NULL,
    updated_at datetime NULL,
    member_id  BIGINT      NOT NULL,
    mission_id BIGINT NULL,
    title      VARCHAR(50) NOT NULL,
    content    TEXT        NOT NULL,
    CONSTRAINT pk_post PRIMARY KEY (id)
);

CREATE TABLE post_tag
(
    id      BIGINT AUTO_INCREMENT NOT NULL,
    tag_id  BIGINT NOT NULL,
    post_id BIGINT NOT NULL,
    CONSTRAINT pk_post_tag PRIMARY KEY (id)
);

CREATE TABLE tag
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(20) NOT NULL,
    CONSTRAINT pk_tag PRIMARY KEY (id)
);

ALTER TABLE member
    ADD CONSTRAINT uc_member_githubid UNIQUE (github_id);

ALTER TABLE member
    ADD CONSTRAINT uc_member_username UNIQUE (username);

ALTER TABLE mission
    ADD CONSTRAINT FK_MISSION_ON_LEVEL FOREIGN KEY (level_id) REFERENCES level (id);

ALTER TABLE post
    ADD CONSTRAINT FK_POST_ON_MEMBER FOREIGN KEY (member_id) REFERENCES member (id);

ALTER TABLE post
    ADD CONSTRAINT FK_POST_ON_MISSION FOREIGN KEY (mission_id) REFERENCES mission (id);

ALTER TABLE post_tag
    ADD CONSTRAINT FK_POST_TAG_ON_POST FOREIGN KEY (post_id) REFERENCES post (id);

ALTER TABLE post_tag
    ADD CONSTRAINT FK_POST_TAG_ON_TAG FOREIGN KEY (tag_id) REFERENCES tag (id);