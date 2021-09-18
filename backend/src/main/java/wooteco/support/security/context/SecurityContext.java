package wooteco.support.security.context;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wooteco.support.security.authentication.Authentication;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SecurityContext {

    private Authentication authentication;
}
