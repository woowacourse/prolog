package kr.co.techcourse.prolog.batch.configuration.schedule;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.converter.DefaultJobParametersConverter;
import org.springframework.batch.core.converter.JobParametersConverter;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.autoconfigure.batch.JobLauncherApplicationRunner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@Component
public class Scheduler implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(Scheduler.class);

    private static final JobParametersConverter CONVERTER = new DefaultJobParametersConverter();

    private final ApplicationArguments applicationArguments;
    private final ScheduleBatchFinder scheduleBatchFinder;
    private final JobLauncher jobLauncher;
    private final JobExplorer jobExplorer;
    private final JobRepository jobRepository;

    public Scheduler(ApplicationArguments applicationArguments,
                     ScheduleBatchFinder scheduleBatchFinder,
                     JobLauncher jobLauncher,
                     JobExplorer jobExplorer,
                     JobRepository jobRepository
    ) {
        this.applicationArguments = applicationArguments;
        this.scheduleBatchFinder = scheduleBatchFinder;
        this.jobLauncher = jobLauncher;
        this.jobExplorer = jobExplorer;
        this.jobRepository = jobRepository;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        doScheduling();
    }

    public void doScheduling() {
        List<BatchJob> batchJobs = scheduleBatchFinder.findBatchJobs();

        ThreadPoolTaskScheduler threadPoolTaskExecutor = new ThreadPoolTaskScheduler();
        threadPoolTaskExecutor.setPoolSize(batchJobs.size());
        threadPoolTaskExecutor.setThreadNamePrefix("[Prolog Batch Scheduler]");
        threadPoolTaskExecutor.initialize();

        for (BatchJob batchJob : batchJobs) {
            threadPoolTaskExecutor.schedule(
                () -> runBatch(batchJob),
                new CronTrigger(batchJob.getCron())
            );
        }
    }

    public void runBatch(BatchJob job) {
        String[] annotationJobParameters = job.getJobParameters();
        String[] argsJobParameters = applicationArguments.getNonOptionArgs().toArray(new String[0]);

        String[] jobParameter = concat(annotationJobParameters, argsJobParameters);

        JobLauncherApplicationRunner jobLauncherApplicationRunner
            = new JobLauncherApplicationRunner(jobLauncher, jobExplorer, jobRepository);

        jobLauncherApplicationRunner.setJobs(List.of(job.getBean()));

        try {
            jobLauncherApplicationRunner.run(jobParameter);
        } catch (Exception e) {
            log.error(String.format("batch name : [%s], exception occurred ", job.getName()), e);
        }
    }

    private String[] concat(String[] a, String[] b) {
        String[] result = new String[a.length + b.length];

        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);

        return result;
    }
}
