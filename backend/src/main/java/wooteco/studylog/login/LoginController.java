package wooteco.studylog.login;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private GithubService githubService;

    public LoginController(GithubService githubService) {
        this.githubService = githubService;
    }

    @PostMapping("/login/token")
    public ResponseEntity<String> login(@RequestBody GithubRequest githubRequest) {
        String accessToken = githubService.login(githubRequest);
        return ResponseEntity.ok().body(accessToken);
    }
}
