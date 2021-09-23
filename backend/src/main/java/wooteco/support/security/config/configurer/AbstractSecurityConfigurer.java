package wooteco.support.security.config.configurer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import wooteco.support.security.config.HttpSecurity;

@AllArgsConstructor
@Getter
public abstract class AbstractSecurityConfigurer implements SecurityConfigurer {

    private HttpSecurity httpSecurity;

    public HttpSecurity and() {
        return httpSecurity;
    }
}
