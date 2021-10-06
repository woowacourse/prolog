package wooteco.support.performance;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PerformanceLoggingForm {

    private String targetApi;
    private String targetMethod;
    private Long transactionTime;
    private Long queryCounts = 0L;
    private Long queryTime = 0L;

    public void queryCountUp() {
        queryCounts++;
    }

    public void addQueryTime(Long queryTime) {
        this.queryTime += queryTime;
    }
}
