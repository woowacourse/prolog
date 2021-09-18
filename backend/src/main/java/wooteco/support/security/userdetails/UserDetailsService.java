package wooteco.support.security.userdetails;

public interface UserDetailsService {

    UserDetails loadUserByUsername(String username);
}
