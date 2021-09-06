package wooteco.prolog.fixtures;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wooteco.prolog.login.application.dto.GithubAccessTokenRequest;
import wooteco.prolog.login.application.dto.GithubAccessTokenResponse;
import wooteco.prolog.login.application.dto.GithubProfileResponse;

@RestController
public class GithubTestController {

    @PostMapping("/github/login/oauth/access_token")
    public ResponseEntity<GithubAccessTokenResponse> accessToken(
            @RequestBody GithubAccessTokenRequest tokenRequest) {
        String accessToken = GithubResponses.findByCode(tokenRequest.getCode()).getAccessToken();
        GithubAccessTokenResponse response = new GithubAccessTokenResponse(accessToken, "", "", "");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/github/user")
    public ResponseEntity<GithubProfileResponse> user(
            @RequestHeader("Authorization") String authorization) {
        String accessToken = authorization.split(" ")[1];
        GithubResponses githubResponse = GithubResponses.findByToken(accessToken);
        GithubProfileResponse response = new GithubProfileResponse(githubResponse.getName(),
                githubResponse.getLogin(), githubResponse.getId(), githubResponse.getAvatarUrl());
        return ResponseEntity.ok(response);
    }
}
