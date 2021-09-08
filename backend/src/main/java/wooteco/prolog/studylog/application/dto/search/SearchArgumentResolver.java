package wooteco.prolog.studylog.application.dto.search;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class SearchArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(SearchParams.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory)
        throws Exception {
        HttpServletRequest nativeRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        Map<String, String> queryParams = extractQueryParamsToMap(nativeRequest);

        return new StudyLogsSearchRequest(
            queryParams.getOrDefault("keyword", null),
            convertToLongList(queryParams.getOrDefault("levels", null)),
            convertToLongList(queryParams.getOrDefault("missions", null)),
            convertToLongList(queryParams.getOrDefault("tags", null)),
            convertToStringList(queryParams.getOrDefault("usernames", null)),
            makePageableDefault(queryParams)
        );
    }

    private List<Long> convertToLongList(String values) {
        if (Objects.isNull(values)) {
            return null;
        }

        return Stream.of(values.split(","))
            .map(Long::parseLong)
            .collect(Collectors.toList());
    }

    private List<String> convertToStringList(String values) {
        if (Objects.isNull(values)) {
            return null;
        }
        return Stream.of(values.split(","))
            .collect(Collectors.toList());
    }

    private Pageable makePageableDefault(Map<String, String> queryParams) {
        return PageRequest.of(
            Integer.parseInt(queryParams.getOrDefault("page", "1")) - 1,
            Integer.parseInt(queryParams.getOrDefault("size", "20")),
            Direction.DESC,
            "id");
    }

    private Map<String, String> extractQueryParamsToMap(HttpServletRequest nativeRequest) {
        Map<String, String> queryParameters = new HashMap<>();
        Enumeration<String> parameterNames = nativeRequest.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            queryParameters.put(key, nativeRequest.getParameter(key));
        }
        return queryParameters;
    }
}
