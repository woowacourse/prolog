package wooteco.studylog.log.web.controller.dto;

import java.util.List;
import java.util.Objects;

public class LogResponses {
    List<LogResponse> logs;

    public LogResponses() {
    }

    public LogResponses(List<LogResponse> logs) {
        this.logs = logs;
    }

    public List<LogResponse> getLogs() {
        return logs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogResponses that = (LogResponses) o;
        return Objects.equals(logs, that.logs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(logs);
    }
}
