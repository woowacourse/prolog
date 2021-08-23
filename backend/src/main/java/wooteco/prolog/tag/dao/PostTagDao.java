package wooteco.prolog.tag.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import wooteco.prolog.tag.domain.Tag;

import java.util.Arrays;
import java.util.List;

import static wooteco.prolog.tag.dao.TagDao.tagRowMapper;

@Repository
public class PostTagDao {

    private final JdbcTemplate jdbcTemplate;

    public PostTagDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert(Long postId, Long tagId) {
        String query = "INSERT INTO post_tag(post_id, tag_id) VALUES(?, ?)";
        this.jdbcTemplate.update(query, postId, tagId);
    }

    public List<Long> findByPostId(Long postId) {
        String query = "SELECT tag_id FROM post_tag WHERE post_id = ?";
        return jdbcTemplate.queryForList(query, Long.class, postId);
    }

    public void delete(Long postId, Long tagId) {
        String query = "DELETE FROM post_tag WHERE post_id = ? AND tag_id = ?";
        this.jdbcTemplate.update(query, postId, tagId);
    }

    public void deleteByPostId(Long postId) {
        String query = "DELETE FROM post_tag WHERE post_id = ?";

        this.jdbcTemplate.update(query, postId);
    }

    public List<Tag> findAll() {
        String query = "SELECT DISTINCT t.id as id, t.name " +
                "FROM post_tag pt " +
                "INNER JOIN tag t ON pt.tag_id = t.id";
        return this.jdbcTemplate.query(query, tagRowMapper);
    }
}
