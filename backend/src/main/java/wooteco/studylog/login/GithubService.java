package wooteco.studylog.login;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class GithubService {
    public String login(GithubRequest githubRequest) {
        String githubAccessToken = getAccessTokenFromGithub(githubRequest);
        GithubProfileResponse githubProfile = getGithubProfileFromGithub(githubAccessToken);

        // TODO: 멤버 저장 & 토큰 생성
        return "study-log-token";
    }

    private String getAccessTokenFromGithub(GithubRequest githubRequest) {
        String url = "https://github.com/login/oauth/access_token";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", githubRequest.getCode());
        params.add("client_id", "f91b56445e08d44adb76");
        params.add("client_secret", "f02769f780ed3d40f9db9283f5b7cc79ecf9074e");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<GithubAccessTokenResponse> result = restTemplate.exchange(url, HttpMethod.POST, httpEntity, GithubAccessTokenResponse.class);
        return result.getBody().getAccessToken();
    }

    private GithubProfileResponse getGithubProfileFromGithub(String accessToken) {
        String url = "https://api.github.com/user";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "token " + accessToken);

        HttpEntity httpEntity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<GithubProfileResponse> result = restTemplate.exchange(url, HttpMethod.GET, httpEntity, GithubProfileResponse.class);

        return result.getBody();
    }
}
