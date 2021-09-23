package wooteco.support.security.oauth2;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OAuth2User {

    private final Map<String, Object> attributes;

}
