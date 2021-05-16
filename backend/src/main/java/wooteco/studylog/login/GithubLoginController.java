package wooteco.studylog.login;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GithubLoginController {

    GithubLoginService githubLoginService;

    public GithubLoginController(GithubLoginService githubLoginService) {
        this.githubLoginService = githubLoginService;
    }

    @PostMapping("/login/token")
    public ResponseEntity<TokenResponse> login(@RequestBody TokenRequest tokenRequest) {
        TokenDto tokenDto = tokenRequest.toTokenDto();
        String accessToken = githubLoginService.authenticate(tokenDto);
        TokenResponse tokenResponse = TokenResponse.of(accessToken);
        return ResponseEntity.ok(tokenResponse);
    }

}
