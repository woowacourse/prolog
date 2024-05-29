ALTER TABLE member
    ADD COLUMN rss_feed_url VARCHAR(256);

ALTER TABLE article
    ADD COLUMN description VARCHAR(256) AFTER title;

ALTER TABLE article
    ADD COLUMN published_at datetime(6) AFTER image_url;
