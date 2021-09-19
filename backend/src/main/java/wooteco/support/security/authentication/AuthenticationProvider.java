package wooteco.support.security.authentication;

public interface AuthenticationProvider {

    Authentication authenticate(AuthenticationToken authenticationToken);

    boolean supports(Class<?> authentication);
}
