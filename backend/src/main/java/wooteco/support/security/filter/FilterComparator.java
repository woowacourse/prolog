package wooteco.support.security.filter;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.Filter;
import org.springframework.web.filter.CorsFilter;
import wooteco.support.security.context.SecurityContextPersistenceFilter;

public class FilterComparator implements Comparator<Filter> {

    private static final int INITIAL_ORDER = 100;
    private static final int ORDER_STEP = 100;
    private final Map<String, Integer> filterToOrder = new HashMap<>();

    public FilterComparator() {
        Step order = new Step(INITIAL_ORDER, ORDER_STEP);
        put(SecurityContextPersistenceFilter.class, order.next());
        put(CorsFilter.class, order.next());
        filterToOrder.put(
            "wooteco.support.security.authentication.oauth2.OAuth2LoginAuthenticationFilter",
            order.next());
    }

    @Override
    public int compare(Filter lhs, Filter rhs) {
        Integer left = getOrder(lhs.getClass());
        Integer right = getOrder(rhs.getClass());
        return left - right;
    }

    private void put(Class<? extends Filter> filter, int position) {
        String className = filter.getName();
        filterToOrder.put(className, position);
    }

    private Integer getOrder(Class<?> clazz) {
        while (clazz != null) {
            Integer result = filterToOrder.get(clazz.getName());
            if (result != null) {
                return result;
            }
            clazz = clazz.getSuperclass();
        }
        return null;
    }

    private static class Step {

        private int value;
        private final int stepSize;

        Step(int initialValue, int stepSize) {
            this.value = initialValue;
            this.stepSize = stepSize;
        }

        int next() {
            int value = this.value;
            this.value += this.stepSize;
            return value;
        }
    }

    public void registerAfter(Class<? extends Filter> filter,
                              Class<? extends Filter> afterFilter) {
        Integer position = getOrder(afterFilter);
        if (position == null) {
            throw new IllegalArgumentException(
                "Cannot register after unregistered Filter " + afterFilter);
        }

        put(filter, position + 1);
    }

    public void registerBefore(Class<? extends Filter> filter,
                               Class<? extends Filter> beforeFilter) {
        Integer position = getOrder(beforeFilter);
        if (position == null) {
            throw new IllegalArgumentException(
                "Cannot register after unregistered Filter " + beforeFilter);
        }

        put(filter, position - 1);
    }
}
