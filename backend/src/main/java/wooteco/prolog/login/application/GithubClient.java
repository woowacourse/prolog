package wooteco.prolog.login.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import wooteco.prolog.login.application.dto.GithubAccessTokenResponse;
import wooteco.prolog.login.application.dto.GithubProfileResponse;

@Component
public class GithubClient {

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.url.access-token}")
    private String tokenUrl;
    @Value("${github.url.profile}")
    private String profileUrl;

    public String getAccessTokenFromGithub(String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);
        RestTemplate restTemplate = new RestTemplate();

        String accessToken = restTemplate
                .exchange(tokenUrl, HttpMethod.POST, httpEntity, GithubAccessTokenResponse.class)
                .getBody()
                .getAccessToken();
        if (accessToken == null) {
            throw new IllegalArgumentException("로그인에 실패했습니다.");
        }
        return accessToken;
    }

    public GithubProfileResponse getGithubProfileFromGithub(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "token " + accessToken);

        HttpEntity httpEntity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        try {
            return restTemplate
                    .exchange(profileUrl, HttpMethod.GET, httpEntity, GithubProfileResponse.class)
                    .getBody();
        } catch (HttpClientErrorException e) {
            throw new IllegalArgumentException("로그인에 실패했습니다.");
        }
    }
}
