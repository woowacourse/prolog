package wooteco.prolog.member.ui;

import java.time.Month;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MonthConverter implements Converter<String, Month> {

    @Override
    public Month convert(String source) {
        return Month.of(Integer.parseInt(source));
    }
}
