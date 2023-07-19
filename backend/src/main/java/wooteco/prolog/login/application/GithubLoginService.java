package wooteco.prolog.login.application;

import static wooteco.prolog.common.exception.BadRequestCode.TOKEN_NOT_VALID;

import org.springframework.stereotype.Service;
import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.login.application.dto.GithubProfileResponse;
import wooteco.prolog.login.application.dto.TokenRequest;
import wooteco.prolog.login.application.dto.TokenResponse;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;

@Service
public class GithubLoginService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;
    private final GithubClient githubClient;

    public GithubLoginService(
        JwtTokenProvider jwtTokenProvider,
        MemberService memberService,
        GithubClient githubClient
    ) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberService = memberService;
        this.githubClient = githubClient;
    }

    public TokenResponse createToken(TokenRequest tokenRequest) {
        String githubAccessToken = githubClient.getAccessTokenFromGithub(tokenRequest.getCode());
        GithubProfileResponse githubProfile = githubClient
            .getGithubProfileFromGithub(githubAccessToken);
        Member member = memberService.findOrCreateMember(githubProfile);
        String accessToken = jwtTokenProvider.createToken(member);
        return TokenResponse.of(accessToken);
    }

    public void validateToken(String credentials) {
        if (!jwtTokenProvider.validateToken(credentials)) {
            throw new BadRequestException(TOKEN_NOT_VALID);
        }
    }
}
