package kr.co.techcourse.prolog.batch.job.common;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;

import java.util.Optional;

public class RunIdOnlyIncrementer implements JobParametersIncrementer {

	private static final String RUN_ID_KEY = "run.id";

	private String key = RUN_ID_KEY;

	/**
	 * The name of the run id in the job parameters.  Defaults to "run.id".
	 *
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * Increment the run.id parameter (starting with 1).
	 */
	@Override
	public JobParameters getNext(JobParameters parameters) {

		JobParameters params = (parameters == null) ? new JobParameters() : parameters;

		long id = Optional.of(params).map(p -> p.getLong(key)).orElse(0L) + 1;
		return new JobParametersBuilder().addLong(key, id).toJobParameters();
	}
}

