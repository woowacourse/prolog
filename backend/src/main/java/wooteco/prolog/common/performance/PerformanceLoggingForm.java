package wooteco.prolog.common.performance;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class PerformanceLoggingForm {

    private static final String LOG_FORM = "요청 api : [{}]  트랜잭션 시간 : [{}ms]  쿼리 개수 : [{}개]  쿼리 총 수행 시간 [{}ms]";

    private Long transactionStartTime;
    private Long transactionEndTime;
    private Long queryTime = 0L;
    private Long queryCounts = 0L;
    private String targetApi;

    public void queryCountUp() {
        queryCounts++;
    }

    public void printLog() {
        log.info(LOG_FORM, targetApi, transactionEndTime - transactionStartTime, queryCounts, queryTime);
    }

    public void addQueryTime(Long queryTime) {
        this.queryTime += queryTime;
    }
}
