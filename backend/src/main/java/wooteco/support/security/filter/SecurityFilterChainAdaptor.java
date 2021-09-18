package wooteco.support.security.filter;

import java.util.Arrays;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;

public class SecurityFilterChainAdaptor {

    public static SecurityFilterChain of(String url, Filter filter) {
        return new SecurityFilterChain() {
            @Override
            public boolean matches(HttpServletRequest request) {
                if ("/*".equalsIgnoreCase(url)) {
                    return true;
                }

                return request.getRequestURI().equalsIgnoreCase(url);
            }

            @Override
            public List<Filter> getFilters() {
                return Arrays.asList(filter);
            }
        };
    }
}
