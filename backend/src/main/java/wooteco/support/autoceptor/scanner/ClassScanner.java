package wooteco.support.autoceptor.scanner;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.util.HashSet;
import java.util.Set;

public class ClassScanner {

    private final String basePackage;

    public ClassScanner(String basePackage) {
        this.basePackage = basePackage;
    }

    public Set<Class<?>> getAllClasses() {
        Reflections reflections = new Reflections(
                basePackage,
                new SubTypesScanner(false)
        );

        return new HashSet<>(reflections.getSubTypesOf(Object.class));
    }
}
