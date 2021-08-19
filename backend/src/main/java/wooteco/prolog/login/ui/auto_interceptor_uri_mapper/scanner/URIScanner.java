package wooteco.prolog.login.ui.auto_interceptor_uri_mapper.scanner;

import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class URIScanner {

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

            uriAndMethods.addAll(mergeUris(controllerUris, methodUris));
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

    private Collection<String> mergeUris(List<String> controllerUris, List<String> methodUris) {
        controllerUris = new ArrayList<>(controllerUris);
        List<String> uris = new ArrayList<>();

        if(controllerUris.isEmpty() && !methodUris.isEmpty()) {
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
