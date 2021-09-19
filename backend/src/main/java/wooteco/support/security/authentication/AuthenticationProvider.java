package wooteco.support.security.authentication;

public interface AuthenticationProvider {

    Authentication authenticate(AuthenticationRequest authentication);

    boolean supports(Class<?> authentication);
}
