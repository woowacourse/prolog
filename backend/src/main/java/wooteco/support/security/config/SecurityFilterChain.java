package wooteco.support.security.config;

import java.util.List;
import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;

public interface SecurityFilterChain {

    boolean matches(HttpServletRequest request);

    List<Filter> getFilters();
}
