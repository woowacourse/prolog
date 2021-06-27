package wooteco.prolog.post.ui;

import org.springframework.core.convert.converter.Converter;
import wooteco.prolog.post.domain.SortBy;

public class StringToEnumConverter implements Converter<String, SortBy> {
    @Override
    public SortBy convert(String source) {
        return SortBy.findByName(source);
    }
}
