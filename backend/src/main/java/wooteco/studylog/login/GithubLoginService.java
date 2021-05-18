package wooteco.studylog.login;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class GithubLoginService {

    private JwtTokenProvider jwtTokenProvider;
    private MemberDao memberDao;

    public GithubLoginService(JwtTokenProvider jwtTokenProvider, MemberDao memberDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberDao = memberDao;
    }

    public String createToken(TokenDto tokenDto) {
        String code = tokenDto.getCode();

        String githubAccessToken = getAccessTokenFromGithub(code);
        GithubProfileResponse githubProfile = getGithubProfileFromGithub(githubAccessToken);

        Member member = memberDao.findByGithubId(githubProfile.getGithubId())
                .orElseGet(() -> memberDao.insert(Member.of(githubProfile)));
        return jwtTokenProvider.createToken(member);
    }

    private String getAccessTokenFromGithub(String code) {
        String url = "https://github.com/login/oauth/access_token";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
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
