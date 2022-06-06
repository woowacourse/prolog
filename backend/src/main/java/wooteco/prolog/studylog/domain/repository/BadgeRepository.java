package wooteco.prolog.studylog.domain.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

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

    public int countLikesByUserIdDuringSessions(Long userId, List<Long> sessions) {
        String sql = "select count(st.id)\n"
            + "from studylog as st \n"
            + "Join likes as l on l.studylog_id = st.id\n"
            + "where st.session_id in (:sessions) and st.member_id = :userId";

        Map<String, Object> map = new HashMap<>();
        map.put("sessions", sessions);
        map.put("userId", userId);
        SqlParameterSource parameters = new MapSqlParameterSource(map);

        return namedParameterJdbcTemplate.query(sql, parameters, COUNT_RESULT_SET_EXTRACTOR);
    }

    public int countStudylogByUserIdDuringSessions(Long userId, List<Long> sessions) {
        String sql = "select count(st.id)\n"
            + "from studylog as st \n"
            + "where st.session_id in (:sessions) and st.member_id = :userId and st.deleted = false";

        Map<String, Object> map = new HashMap<>();
        map.put("sessions", sessions);
        map.put("userId", userId);
        SqlParameterSource parameters = new MapSqlParameterSource(map);

        return namedParameterJdbcTemplate.query(sql, parameters, COUNT_RESULT_SET_EXTRACTOR);
    }
}
