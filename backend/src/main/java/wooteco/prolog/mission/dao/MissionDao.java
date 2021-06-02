package wooteco.prolog.mission.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import wooteco.prolog.mission.domain.Mission;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class MissionDao {

    private final JdbcTemplate jdbcTemplate;

    public MissionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Mission insert(Mission mission) {
        String query = "INSERT INTO mission(name) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.jdbcTemplate.update(con -> {
            PreparedStatement pstmt = con.prepareStatement(
                    query,
                    new String[]{"id"});
            pstmt.setString(1, mission.getName());
            return pstmt;
        }, keyHolder);

        Long id = keyHolder.getKeyAs(Long.class);
        return new Mission(id, mission.getName());
    }

    public Optional<Mission> findByName(String name) {
        String query = "SELECT * FROM mission WHERE name = ?";
        try {
            Mission mission = jdbcTemplate.queryForObject(query, missionRowMapper, name);
            return Optional.ofNullable(mission);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Mission> findById(Long id) {
        String query = "SELECT * FROM mission WHERE id = ?";
        try {
            Mission mission = jdbcTemplate.queryForObject(query, missionRowMapper, id);
            return Optional.ofNullable(mission);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Mission> findAll() {
        String query = "SELECT * FROM mission";
        return jdbcTemplate.query(query, missionRowMapper);
    }

    private static RowMapper<Mission> missionRowMapper =
            (rs, rowNum) -> {
                long id = rs.getLong(1);
                String name = rs.getString(2);
                return new Mission(id, name);
            };
}
