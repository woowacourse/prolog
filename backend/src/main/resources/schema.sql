-- -----------------------------------------------------
-- Table mydb.post
-- -----------------------------------------------------
DROP TABLE IF EXISTS post ;

CREATE TABLE IF NOT EXISTS post (
    id BIGINT NOT NULL AUTO_INCREMENT,
    member_id BIGINT NOT NULL,
    createdAt DATETIME NOT NULL,
    updatedAt DATETIME NOT NULL,
    title VARCHAR(50) NOT NULL,
    content VARCHAR (65535) NOT NULL,
    category_id BIGINT NOT NULL,
    PRIMARY KEY (id)
    );
-- -----------------------------------------------------
-- Table category
-- -----------------------------------------------------
DROP TABLE IF EXISTS category ;

CREATE TABLE IF NOT EXISTS category (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(45) NOT NULL,
    PRIMARY KEY (id)
);
-- -----------------------------------------------------
-- Table tag
-- -----------------------------------------------------
DROP TABLE IF EXISTS tag ;

CREATE TABLE IF NOT EXISTS tag (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL,
    PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Table postTag
-- -----------------------------------------------------
DROP TABLE IF EXISTS postTag ;

CREATE TABLE IF NOT EXISTS postTag (
    id BIGINT NOT NULL AUTO_INCREMENT,
    post_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT post_id
    FOREIGN KEY (post_id)
    REFERENCES post (id),
    CONSTRAINT tag_id
    FOREIGN KEY (tag_id)
    REFERENCES tag (id)
);
create table if not exists MEMBER
(
    id bigint auto_increment not null,
    nickname varchar(255) not null,
    role varchar(20) not null,
    github_id bigint not null unique,
    image_url varchar(255) not null,
    primary key(id)
);
