package wooteco.prolog.login.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.login.application.GithubLoginService;
import wooteco.prolog.login.application.dto.TokenRequest;
import wooteco.prolog.login.application.dto.TokenResponse;

@RestController
public class GithubLoginController {
    GithubLoginService githubLoginService;

    public GithubLoginController(GithubLoginService githubLoginService) {
        this.githubLoginService = githubLoginService;
    }

    @PostMapping("/login/token")
    public ResponseEntity<TokenResponse> login(@RequestBody TokenRequest tokenRequest) {
        return ResponseEntity.ok(githubLoginService.createToken(tokenRequest));
    }
}
