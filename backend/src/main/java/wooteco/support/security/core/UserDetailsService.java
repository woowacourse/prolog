package wooteco.support.security.core;

public interface UserDetailsService {

    UserDetails loadUserByUsername(String username);
}
