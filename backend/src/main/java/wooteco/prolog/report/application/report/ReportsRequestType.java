package wooteco.prolog.report.application.report;

import org.springframework.data.domain.Pageable;

public interface ReportsRequestType {
    boolean isSupport(String type);
    Object execute(String username, Pageable pageable);
}
