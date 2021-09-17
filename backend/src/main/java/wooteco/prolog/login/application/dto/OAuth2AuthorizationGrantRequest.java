package wooteco.prolog.login.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OAuth2AuthorizationGrantRequest {

    private String code;
}
