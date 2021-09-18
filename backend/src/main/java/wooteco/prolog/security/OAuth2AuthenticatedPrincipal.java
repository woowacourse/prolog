package wooteco.prolog.security;

import java.util.Map;

public interface OAuth2AuthenticatedPrincipal {

    Map<String, Object> getAttributes();
}
