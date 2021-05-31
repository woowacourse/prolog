package wooteco.prolog.login.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.login.TokenResponse;
import wooteco.prolog.login.application.GithubLoginService;
import wooteco.prolog.login.application.dto.ErrorMessage;
import wooteco.prolog.login.application.dto.TokenRequest;

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

    //Todo : Custom Exception 으로 변경
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorMessage(e.getMessage()));
    }
}
