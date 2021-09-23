package wooteco.support.security.config.configurer;

import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import wooteco.support.security.config.HttpSecurity;

public class CorsConfigurer extends AbstractSecurityConfigurer {

    private CorsConfigurationSource configurationSource;

    public CorsConfigurer(HttpSecurity httpSecurity) {
        super(httpSecurity);
    }

    public CorsConfigurer configurationSource(CorsConfigurationSource configurationSource) {
        this.configurationSource = configurationSource;
        return this;
    }

    @Override
    public void configure(HttpSecurity http) {
        http.addFilter(new CorsFilter(configurationSource));
    }
}
