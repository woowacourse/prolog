package wooteco.prolog.login.aop;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import wooteco.prolog.login.ui.LoginMember.Authority;

@Component
@RequestScope
public class MemberAuthorityCache {

    private Authority authority;

    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }
}
