package wooteco.support.security.config.configurer;

import wooteco.support.security.config.HttpSecurity;
import wooteco.support.security.context.SecurityContextPersistenceFilter;

public class SecurityContextConfigurer implements SecurityConfigurer {

    @Override
    public void configure(HttpSecurity http) {
        http.addFilter(new SecurityContextPersistenceFilter());
    }
}
