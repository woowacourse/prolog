-- -----------------------------------------------------
-- Table mydb.post
-- -----------------------------------------------------
DROP TABLE IF EXISTS post ;

CREATE TABLE IF NOT EXISTS post (
                                    id BIGINT NOT NULL AUTO_INCREMENT,
                                    member_id BIGINT NOT NULL,
                                    createdAt DATETIME NOT NULL,
                                    updatedAt DATETIME NULL,
                                    title VARCHAR(65535) NOT NULL,
    category_id BIGINT NOT NULL,
    PRIMARY KEY (id)
    ) ENGINE = InnoDB;
-- -----------------------------------------------------
-- Table category
-- -----------------------------------------------------
DROP TABLE IF EXISTS category ;

CREATE TABLE IF NOT EXISTS category (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(45) NOT NULL,
    PRIMARY KEY (id)
)ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table tag
-- -----------------------------------------------------
DROP TABLE IF EXISTS tag ;

CREATE TABLE IF NOT EXISTS tag (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL,
    PRIMARY KEY (id)
)ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table postTag
-- -----------------------------------------------------
DROP TABLE IF EXISTS postTag ;

CREATE TABLE IF NOT EXISTS postTag (
    id BIGINT NOT NULL AUTO_INCREMENT,
    post_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    INDEX post_id_idx (post_id ASC) VISIBLE,
    INDEX tag_id_idx (tag_id ASC) VISIBLE,
    CONSTRAINT post_id
    FOREIGN KEY (post_id)
    REFERENCES post (id),
    CONSTRAINT tag_id
    FOREIGN KEY (tag_id)
    REFERENCES tag (id)
) ENGINE = InnoDB;

