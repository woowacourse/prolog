package wooteco.prolog.login.application;

import org.springframework.stereotype.Service;
import wooteco.prolog.login.application.dto.GithubProfileResponse;
import wooteco.prolog.login.application.dto.TokenRequest;
import wooteco.prolog.login.application.dto.TokenResponse;
import wooteco.prolog.login.excetpion.TokenNotValidException;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;

@Service
public class GithubLoginService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberDao;
    private final GithubClient githubClient;

    public GithubLoginService(
            JwtTokenProvider jwtTokenProvider,
            MemberService memberDao,
            GithubClient githubClient
    ) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberDao = memberDao;
        this.githubClient = githubClient;
    }

    public TokenResponse createToken(TokenRequest tokenRequest) {
        String githubAccessToken = githubClient.getAccessTokenFromGithub(tokenRequest.getCode());
        GithubProfileResponse githubProfile = githubClient.getGithubProfileFromGithub(githubAccessToken);
        Member member = memberDao.findOrCreateMember(githubProfile);
        String accessToken = jwtTokenProvider.createToken(member);
        return TokenResponse.of(accessToken);
    }

    public void validateToken(String credentials) {
        if (!jwtTokenProvider.validateToken(credentials)) {
            throw new TokenNotValidException();
        }
    }
}
