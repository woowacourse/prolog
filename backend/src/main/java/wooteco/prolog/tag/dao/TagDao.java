package wooteco.prolog.tag.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import wooteco.prolog.tag.domain.Tag;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class TagDao {

    private JdbcTemplate jdbcTemplate;

    public TagDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Tag> findAll() {
        String query = "SELECT * FROM tag";
        return jdbcTemplate.query(query, tagRowMapper);
    }

    private static RowMapper<Tag> tagRowMapper =
            (rs, rowNum) -> {
                long id = rs.getLong(1);
                String name = rs.getString(2);
                return new Tag(id, name);
            };

    public Tag insert(String name) {
        String query = "INSERT INTO Tag(name) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.jdbcTemplate.update(con -> {
            PreparedStatement pstmt = con.prepareStatement(
                    query,
                    new String[]{"id"});
            pstmt.setString(1, name);
            return pstmt;
        }, keyHolder);

        Long id = keyHolder.getKeyAs(Long.class);
        return new Tag(id, name);
    }

    public Tag findById(Long id) {
        String query = "SELECT * FROM tag WHERE id = ?";
        return jdbcTemplate.queryForObject(query, tagRowMapper, id);
    }
}
