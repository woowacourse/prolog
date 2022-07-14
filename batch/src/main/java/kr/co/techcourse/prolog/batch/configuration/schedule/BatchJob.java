package kr.co.techcourse.prolog.batch.configuration.schedule;

import org.springframework.batch.core.Job;

public class BatchJob {

    private final String cron;
    private final String[] jobParameter;
    private final char delimiter;
    private final Job jobBean;

    public BatchJob(String cron,
                    String[] jobParameter,
                    char delimiter,
                    Job jobBean) {
        this.cron = cron;
        this.jobParameter = jobParameter;
        this.delimiter = delimiter;
        this.jobBean = jobBean;
    }
}