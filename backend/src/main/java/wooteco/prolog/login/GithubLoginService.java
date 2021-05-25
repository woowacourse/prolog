package wooteco.prolog.login;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class GithubLoginService {
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDao memberDao;

    public GithubLoginService(JwtTokenProvider jwtTokenProvider, MemberDao memberDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberDao = memberDao;
    }

    public String createToken(String code) {
        String githubAccessToken = getAccessTokenFromGithub(code);
        GithubProfileResponse githubProfile = getGithubProfileFromGithub(githubAccessToken);

        Member member = findOrCreateMember(githubProfile);
        return jwtTokenProvider.createToken(member);
    }

    private String getAccessTokenFromGithub(String code) {
        String url = "https://github.com/login/oauth/access_token";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);
        RestTemplate restTemplate = new RestTemplate();

        String accessToken = restTemplate
                .exchange(url, HttpMethod.POST, httpEntity, GithubAccessTokenResponse.class)
                .getBody()
                .getAccessToken();
        if (accessToken == null) {
            throw new IllegalArgumentException("로그인에 실패했습니다.");
        }
        return accessToken;
    }

    private GithubProfileResponse getGithubProfileFromGithub(String accessToken) {
        String url = "https://api.github.com/user";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "token " + accessToken);

        HttpEntity httpEntity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        try {
            return restTemplate
                    .exchange(url, HttpMethod.GET, httpEntity, GithubProfileResponse.class)
                    .getBody();
        } catch (HttpClientErrorException e) {
            throw new IllegalArgumentException("github 정보 조회 실패");
        }
    }

    private Member findOrCreateMember(GithubProfileResponse githubProfile) {
        return memberDao.findByGithubId(githubProfile.getGithubId())
                .orElseGet(() -> memberDao.insert(Member.of(githubProfile)));
    }
}
