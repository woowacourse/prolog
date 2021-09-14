package wooteco.prolog.studylog.domain.report.common;

import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class UpdateUtil {

    private UpdateUtil() {
    }

    public static <T extends Updatable<T>> void execute(List<T> origin, List<T> source) {
        update(origin, source);
        delete(origin, source);
        create(origin, source);
    }

    private static <T extends Updatable<T>> void update(List<T> origin, List<T> source) {
        Map<T, T> origins = origin.stream()
            .collect(toMap(Function.identity(), Function.identity()));

        source.stream()
            .filter(origins::containsKey)
            .forEach(s -> origins.get(s).update(s));
    }

    private static <T extends Updatable<T>> void delete(List<T> origin, List<T> source) {
        origin.removeIf(o -> !source.contains(o));
    }

    private static <T extends Updatable<T>> void create(List<T> origin, List<T> source) {
        source.stream()
            .filter(s -> !origin.contains(s))
            .forEach(origin::add);
    }
}
