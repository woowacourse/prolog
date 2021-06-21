package wooteco.prolog.login;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.login.application.dto.GithubAccessTokenResponse;
import wooteco.prolog.login.application.dto.GithubProfileResponse;
import wooteco.prolog.login.domain.Member;

import static wooteco.prolog.Documentation.MEMBER1;

@Profile("test")
@RestController
public class GithubTestController {
    @GetMapping("/github/user")
    public ResponseEntity<GithubProfileResponse> user() {
        GithubProfileResponse response = new GithubProfileResponse(MEMBER1.getNickname(), MEMBER1.getNickname(), String.valueOf(MEMBER1.getGithubId()), MEMBER1.getImageUrl());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/github/login/oauth/access_token")
    public ResponseEntity<GithubAccessTokenResponse> accessToken() {
        GithubAccessTokenResponse response = new GithubAccessTokenResponse("액세스토큰", "", "", "");
        return ResponseEntity.ok(response);
    }
}
