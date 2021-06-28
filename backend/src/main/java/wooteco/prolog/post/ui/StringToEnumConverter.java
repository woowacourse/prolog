package wooteco.prolog.post.ui;

import org.springframework.core.convert.converter.Converter;
import wooteco.prolog.post.domain.Direction;

public class StringToEnumConverter implements Converter<String, Direction> {
    @Override
    public Direction convert(String source) {
        return Direction.findByName(source);
    }
}
