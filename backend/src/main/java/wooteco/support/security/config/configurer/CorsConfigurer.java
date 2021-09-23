package wooteco.support.security.config.configurer;

import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import wooteco.support.security.config.HttpSecurity;

public class CorsConfigurer implements SecurityConfigurer {

    private CorsConfigurationSource configurationSource;

    public CorsConfigurer configurationSource(CorsConfigurationSource configurationSource) {
        this.configurationSource = configurationSource;
        return this;
    }

    @Override
    public void configure(HttpSecurity http) {
        http.addFilter(new CorsFilter(configurationSource));
    }
}
