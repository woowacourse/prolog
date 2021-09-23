package wooteco.support.security.oauth2;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProviderDetails {

    private String tokenUri;
    private String userInfoUri;
}
