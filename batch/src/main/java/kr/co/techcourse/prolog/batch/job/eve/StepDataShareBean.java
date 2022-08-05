package kr.co.techcourse.prolog.batch.job.eve;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class StepDataShareBean<T> {

    private final Map<String, T> dataContainer;

    public StepDataShareBean() {
        dataContainer = new HashMap<>();
    }

    public void put(String key, T data) {
        dataContainer.put(key, data);
    }

    public T get(String key) {
        return dataContainer.get(key);
    }
}
