package kr.co.techcourse.prolog.batch.configuration.schedule;

import org.springframework.batch.core.Job;

public class BatchJob {

    private final String name;
    private final String cron;
    private final String[] jobParameters;
    private final Job jobBean;

    public BatchJob(String name,
                    String cron,
                    String[] jobParameters,
                    Job jobBean
    ) {
        this.name = name;
        this.cron = cron;
        this.jobParameters = jobParameters;
        this.jobBean = jobBean;
    }

    public String getName() {
        return name;
    }

    public String getCron() {
        return cron;
    }

    public String[] getJobParameters() {
        return jobParameters;
    }

    public Job getBean() {
        return jobBean;
    }
}