package wooteco.prolog.member.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private RowMapper<Member> rowMapper = (rs, rowNum) ->
            new Member(
                    rs.getLong("id"),
                    rs.getString("username"),
                    rs.getString("nickname"),
                    Role.of(rs.getString("role")),
                    rs.getLong("github_id"),
                    rs.getString("image_url")
            );

    public MemberDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("member")
                .usingGeneratedKeyColumns("id");
    }

    public Member insert(Member member) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(member);
        Long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return new Member(id, member.getUsername(), member.getNickname(), member.getRole(), member.getGithubId(), member.getImageUrl());
    }

    public Optional<Member> findById(final Long userId) {
        String sql = "SELECT * FROM member WHERE id = ?";
        List<Member> result = jdbcTemplate.query(sql, rowMapper, userId);
        if (result.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(result.get(0));
    }

    public Optional<Member> findByGithubId(final Long githubId) {
        String sql = "SELECT * FROM member WHERE github_id = ?";
        List<Member> result = jdbcTemplate.query(sql, rowMapper, githubId);
        if (result.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(result.get(0));
    }

    public Optional<Member> findByUsername(String username) {
        String sql = "SELECT * FROM member WHERE username = ?";
        List<Member> result = jdbcTemplate.query(sql, rowMapper, username);
        if (result.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(result.get(0));
    }

    public void updateMember(Member updatedMember) {
        String query = "UPDATE member SET username = ?, nickname = ?, image_url = ? WHERE id = ?";

        this.jdbcTemplate.update(
                query,
                updatedMember.getUsername(),
                updatedMember.getNickname(),
                updatedMember.getImageUrl(),
                updatedMember.getId()
        );
    }
}
