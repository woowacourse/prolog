package wooteco.prolog.member.domain;

import wooteco.support.security.jwt.JwtAuthentication;

public class LoginMember extends JwtAuthentication {

    public LoginMember(String username) {
        super(username);
    }

    public LoginMember(JwtAuthentication authentication) {
        super(authentication.getUsername());
    }

    public static LoginMember of(Member member) {
        return new LoginMember(
            member.getUsername()
        );
    }
}
