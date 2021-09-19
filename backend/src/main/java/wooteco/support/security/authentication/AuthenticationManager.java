package wooteco.support.security.authentication;

public interface AuthenticationManager {

    Authentication authenticate(AuthenticationRequest authenticationRequest);
}
