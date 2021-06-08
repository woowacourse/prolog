DROP TABLE IF EXISTS postTag ;
DROP TABLE IF EXISTS post ;
DROP TABLE IF EXISTS tag ;
DROP TABLE IF EXISTS mission ;
DROP TABLE IF EXISTS member;

-- -----------------------------------------------------
-- Table mydb.post
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS post
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    member_id BIGINT NOT NULL,
    createdAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    title VARCHAR(50) NOT NULL,
    content TEXT NOT NULL,
    mission_id BIGINT NOT NULL,
    PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Table mission
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS mission
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(45) NOT NULL,
    PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Table tag
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS tag
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL,
    PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Table postTag
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS postTag
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    post_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    PRIMARY KEY (id),CONSTRAINT post_id
    FOREIGN KEY (post_id)
    REFERENCES post (id),
    CONSTRAINT tag_id
    FOREIGN KEY (tag_id)
    REFERENCES tag (id)
);


-- -----------------------------------------------------
-- Table member
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS member
(
    id bigint NOT NULL AUTO_INCREMENT,
    nickname VARCHAR(255),
    role VARCHAR(20) NOT NULL,
    github_id bigint NOT NULL UNIQUE,
    image_url VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
