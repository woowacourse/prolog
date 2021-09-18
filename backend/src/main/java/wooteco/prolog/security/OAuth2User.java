package wooteco.prolog.security;

import java.util.Map;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OAuth2User implements OAuth2AuthenticatedPrincipal {

    private final Map<String, Object> attributes;

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
