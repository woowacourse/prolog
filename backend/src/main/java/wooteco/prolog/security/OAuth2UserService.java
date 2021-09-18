package wooteco.prolog.security;

public interface OAuth2UserService {

    OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest);
}
