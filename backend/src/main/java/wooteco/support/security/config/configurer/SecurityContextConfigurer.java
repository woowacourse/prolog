package wooteco.support.security.config.configurer;

import wooteco.support.security.config.HttpSecurity;
import wooteco.support.security.context.SecurityContextPersistenceFilter;

public class SecurityContextConfigurer extends AbstractSecurityConfigurer {

    public SecurityContextConfigurer(HttpSecurity httpSecurity) {
        super(httpSecurity);
    }

    @Override
    public void configure(HttpSecurity http) {
        http.addFilter(new SecurityContextPersistenceFilter());
    }
}
