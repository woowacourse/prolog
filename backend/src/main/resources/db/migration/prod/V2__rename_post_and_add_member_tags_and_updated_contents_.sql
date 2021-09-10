ALTER TABLE post rename studylog;
ALTER TABLE post_tag rename studylog_tag;

CREATE TABLE updated_contents
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    update_content VARCHAR(255) NULL,
    updated        INT NULL,
    CONSTRAINT pk_updatedcontents PRIMARY KEY (id)
);

CREATE TABLE member_tag
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    member_id BIGINT NOT NULL,
    tag_id    BIGINT NOT NULL,
    count     INT NULL,
    CONSTRAINT pk_member_tag PRIMARY KEY (id)
);