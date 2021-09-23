package wooteco.support.security.oauth2;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import wooteco.support.security.exception.AuthenticationException;
import wooteco.support.security.oauth2.github.GithubAccessTokenRequest;
import wooteco.support.security.oauth2.github.GithubAccessTokenResponse;

public class OAuth2AccessTokenResponseClient {

    public OAuth2AccessTokenResponse getTokenResponse(
        OAuth2AuthenticationToken grantRequest) {
        GithubAccessTokenRequest githubAccessTokenRequest = new GithubAccessTokenRequest(
            grantRequest.getExchange().getCode(),
            grantRequest.getClientRegistration().getClientId(),
            grantRequest.getClientRegistration().getClientSecret()
        );

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity(
            githubAccessTokenRequest, headers);
        RestTemplate restTemplate = new RestTemplate();

        String tokenUrl = grantRequest.getClientRegistration().getProviderDetails().getTokenUri();

        GithubAccessTokenResponse tokenResponse = restTemplate
            .exchange(tokenUrl, HttpMethod.POST, httpEntity, GithubAccessTokenResponse.class)
            .getBody();

        if (tokenResponse.getAccessToken() == null) {
            throw new AuthenticationException("소셜 로그인의 엑세스 토큰을 받아오는 데 실패했습니다.");
        }

        return new OAuth2AccessTokenResponse(tokenResponse.getAccessToken(), "");
    }
}
