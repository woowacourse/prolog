package wooteco.support.autoceptor.scanner;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import org.springframework.util.StringUtils;

public class URIScanner {

    public static final String REGEX = "\\{.*?.}";

    private final ControllerScanner controllerScanner;
    private final MethodScanner methodScanner;

    public URIScanner(ControllerScanner controllerScanner, MethodScanner methodScanner) {
        this.controllerScanner = controllerScanner;
        this.methodScanner = methodScanner;
    }

    public List<String> extractUri() {
        List<Class<?>> controllers = controllerScanner.extractControllers();
        List<String> uriAndMethods = new ArrayList<>();

        for (Class<?> controller : controllers) {
            List<String> controllerUris = extractControllerUri(controller);

            List<Method> methods = methodScanner.extractMethodAnnotatedOnParameter(controller);
            List<String> methodUris = extractMethodUri(methods);

            uriAndMethods.addAll(createUris(controllerUris, methodUris));
        }

        return uriAndMethods;
    }

    private List<String> extractControllerUri(Class<?> controller) {
        return MappingAnnotation.extractUriFrom(controller);
    }

    private List<String> extractMethodUri(List<Method> methods) {
        return methods.stream()
            .map(MappingAnnotation::extractUriFrom)
            .flatMap(Collection::stream)
            .collect(toList());
    }

    private Collection<String> createUris(List<String> controllerUris, List<String> methodUris) {
        return mergeUris(controllerUris, methodUris).stream()
            .map(uri -> uri.replaceAll(REGEX, "*"))
            .collect(toList());
    }

    private Collection<String> mergeUris(List<String> controllerUris, List<String> methodUris) {
        controllerUris = new ArrayList<>(controllerUris);
        Set<String> uris = new HashSet<>();

        if (controllerUris.isEmpty() && !methodUris.isEmpty()) {
            controllerUris.add("");
        }

        for (String controllerUri : controllerUris) {
            for (String methodUri : methodUris) {
                uris.add(createUri(controllerUri, methodUri));
            }
        }

        return uris;
    }

    private String createUri(String controllerUri, String methodUri) {
        return "/" + Stream.of(controllerUri.split("/"), methodUri.split("/"))
            .flatMap(Arrays::stream)
            .filter(StringUtils::hasText)
            .collect(joining("/"));

    }
}
