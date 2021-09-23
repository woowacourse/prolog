package wooteco.support.security.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.filter.GenericFilterBean;

@AllArgsConstructor
@Getter
public class FilterChainProxy extends GenericFilterBean {

    private List<SecurityFilterChain> filterChains;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        List<Filter> filters = getFilters((HttpServletRequest) request);

        VirtualFilterChain vfc = new VirtualFilterChain(chain, filters);
        vfc.doFilter(request, response);
    }

    private List<Filter> getFilters(HttpServletRequest request) {
        List<Filter> result = new ArrayList<>();

        for (SecurityFilterChain chain : filterChains) {
            if (chain.matches(request)) {
                result.addAll(chain.getFilters());
            }
        }

        return result;
    }

    private static class VirtualFilterChain implements FilterChain {

        private final FilterChain originalChain;
        private final List<Filter> additionalFilters;
        private final int size;
        private int currentPosition = 0;

        private VirtualFilterChain(FilterChain chain, List<Filter> additionalFilters) {
            this.originalChain = chain;
            this.additionalFilters = additionalFilters;
            this.size = additionalFilters.size();
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
            if (currentPosition == size) {
                originalChain.doFilter(request, response);
            } else {
                currentPosition++;

                Filter nextFilter = additionalFilters.get(currentPosition - 1);

                nextFilter.doFilter(request, response, this);
            }
        }
    }
}
