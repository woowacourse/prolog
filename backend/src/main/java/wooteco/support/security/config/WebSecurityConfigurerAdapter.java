package wooteco.support.security.config;

import wooteco.support.security.config.configurer.SecurityConfigurer;

public abstract class WebSecurityConfigurerAdapter implements SecurityConfigurer {

    public void configure(HttpSecurity http) {
    }
}
