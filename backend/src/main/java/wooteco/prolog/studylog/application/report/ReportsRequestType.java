package wooteco.prolog.studylog.application.report;

import org.springframework.data.domain.Pageable;

public interface ReportsRequestType {
    boolean isSupport(String type);
    Object execute(String username, Pageable pageable);
}
