package wooteco.support.security.client;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProviderDetails {

    private String tokenUri;
    private String userInfoUri;
}
