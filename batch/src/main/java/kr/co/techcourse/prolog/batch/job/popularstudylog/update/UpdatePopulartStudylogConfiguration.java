package kr.co.techcourse.prolog.batch.job.popularstudylog.update;

import java.util.List;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.context.annotation.Bean;

public class UpdatePopulartStudylogConfiguration {

    @Bean
    public ItemReader<Object> itemReader() {
        return new ItemReader<Object>() {
            @Override
            public Object read()
                throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                return null;
            }
        };
    }

    @Bean
    public ItemProcessor<?, ?> itemProcessor() {
        return new ItemProcessor() {
            @Override
            public Object process(Object item) throws Exception {
                return null;
            }
        };
    }

    @Bean
    public ItemWriter<?> itemWriter() {
        return new ItemWriter<Object>() {
            @Override
            public void write(List<?> items) throws Exception {

            }
        };
    }
}
