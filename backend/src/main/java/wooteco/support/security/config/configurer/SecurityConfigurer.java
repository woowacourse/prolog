package wooteco.support.security.config.configurer;

import wooteco.support.security.config.HttpSecurity;

public interface SecurityConfigurer {

    void configure(HttpSecurity http);
}
