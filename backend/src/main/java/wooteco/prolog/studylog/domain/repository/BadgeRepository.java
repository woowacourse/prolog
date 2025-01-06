package wooteco.prolog.studylog.domain.repository;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BadgeRepository {

    private static final ResultSetExtractor<Integer> COUNT_RESULT_SET_EXTRACTOR = rs -> {
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    };
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public BadgeRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public int countStudylogByUsernameDuringSessions(String username, List<Long> sessions) {
        String sql = "select count(st.id)\n"
            + "from studylog as st \n"
            + "Join session as s on st.session_id = s.id\n"
            + "Join member as mem on st.member_id = mem.id\n"
            + "where s.id in (:sessions) and mem.username = :username and st.deleted = false";

        Map<String, Object> map = new HashMap<>();
        map.put("sessions", sessions);
        map.put("username", username);
        SqlParameterSource parameters = new MapSqlParameterSource(map);

        return namedParameterJdbcTemplate.query(sql, parameters, COUNT_RESULT_SET_EXTRACTOR);
    }

    public int countLikesByUsernameDuringSessions(String username, List<Long> sessions) {
        String sql = "select count(st.id)\n"
            + "from studylog as st \n"
            + "Join session as s on st.session_id = s.id\n"
            + "Join likes as l on l.studylog_id = st.id\n"
            + "Join member as mem on l.member_id = mem.id\n"
            + "where s.id in (:sessions) and mem.username = :username";

        Map<String, Object> map = new HashMap<>();
        map.put("sessions", sessions);
        map.put("username", username);
        SqlParameterSource parameters = new MapSqlParameterSource(map);

        return namedParameterJdbcTemplate.query(sql, parameters, COUNT_RESULT_SET_EXTRACTOR);
    }
}
