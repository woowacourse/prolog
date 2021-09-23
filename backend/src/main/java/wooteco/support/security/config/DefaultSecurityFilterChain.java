package wooteco.support.security.config;

import java.util.List;
import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DefaultSecurityFilterChain implements SecurityFilterChain {

    private final List<Filter> filters;

    @Override
    public boolean matches(HttpServletRequest request) {
        return true;
    }

    @Override
    public List<Filter> getFilters() {
        return filters;
    }
}
