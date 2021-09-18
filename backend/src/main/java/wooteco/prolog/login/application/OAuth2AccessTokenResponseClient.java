package wooteco.prolog.login.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import wooteco.prolog.login.application.dto.GithubAccessTokenRequest;
import wooteco.prolog.login.application.dto.GithubAccessTokenResponse;
import wooteco.prolog.login.application.dto.GithubProfileResponse;
import wooteco.prolog.login.excetpion.GithubApiFailException;
import wooteco.prolog.login.excetpion.GithubConnectionException;
import wooteco.prolog.member.domain.GithubOAuth2User;
import wooteco.prolog.security.OAuth2AccessTokenResponse;
import wooteco.prolog.security.OAuth2User;
import wooteco.prolog.security.OAuth2UserRequest;

@Component
public class OAuth2AccessTokenResponseClient {

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.url.access-token}")
    private String tokenUrl;
    @Value("${github.url.profile}")
    private String profileUrl;

    public OAuth2AccessTokenResponse getTokenResponse(String code) {
        GithubAccessTokenRequest githubAccessTokenRequest = new GithubAccessTokenRequest(
            code,
            clientId,
            clientSecret
        );

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity(
            githubAccessTokenRequest, headers);
        RestTemplate restTemplate = new RestTemplate();

        GithubAccessTokenResponse tokenResponse = restTemplate
            .exchange(tokenUrl, HttpMethod.POST, httpEntity, GithubAccessTokenResponse.class)
            .getBody();

        if (tokenResponse.getAccessToken() == null) {
            throw new GithubApiFailException();
        }

        return new OAuth2AccessTokenResponse(tokenResponse.getAccessToken(), "");
    }

    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "token " + oAuth2UserRequest.getAccessToken());

        HttpEntity httpEntity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        try {
            GithubProfileResponse response = restTemplate
                .exchange(profileUrl, HttpMethod.GET, httpEntity, GithubProfileResponse.class)
                .getBody();
            return new GithubOAuth2User(new ObjectMapper().convertValue(response, Map.class));

        } catch (HttpClientErrorException e) {
            throw new GithubConnectionException();
        }
    }
}
