package wooteco.prolog.login.application;

import org.springframework.stereotype.Service;
import wooteco.prolog.login.application.dto.GithubProfileResponse;
import wooteco.prolog.login.application.dto.TokenRequest;
import wooteco.prolog.login.application.dto.TokenResponse;
import wooteco.prolog.login.dao.MemberDao;
import wooteco.prolog.login.domain.Member;
import wooteco.prolog.login.excetpion.TokenNotValidException;

@Service
public class GithubLoginService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDao memberDao;
    private final GithubClient githubClient;

    public GithubLoginService(
            JwtTokenProvider jwtTokenProvider,
            MemberDao memberDao,
            GithubClient githubClient
    ) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberDao = memberDao;
        this.githubClient = githubClient;
    }

    public TokenResponse createToken(TokenRequest tokenRequest) {
        String githubAccessToken = githubClient.getAccessTokenFromGithub(tokenRequest.getCode());
        GithubProfileResponse githubProfile = githubClient.getGithubProfileFromGithub(githubAccessToken);
        Member member = findOrCreateMember(githubProfile);
        String accessToken = jwtTokenProvider.createToken(member);
        return TokenResponse.of(accessToken);
    }

    public void validateToken(String credentials) {
        if (!jwtTokenProvider.validateToken(credentials)) {
            throw new TokenNotValidException("JWT 토큰이 유효하지 않습니다.");
        }
    }

    private Member findOrCreateMember(GithubProfileResponse githubProfile) {
        return memberDao.findByGithubId(githubProfile.getGithubId())
                .orElseGet(() -> memberDao.insert(Member.of(githubProfile)));
    }
}
