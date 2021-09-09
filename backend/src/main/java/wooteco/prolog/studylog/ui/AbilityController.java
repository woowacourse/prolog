package wooteco.prolog.studylog.ui;

import static org.springframework.http.HttpStatus.CREATED;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.studylog.application.AbilityService;
import wooteco.prolog.studylog.application.dto.ability.AbilityRequest;

@RestController
@RequestMapping("/abilities")
public class AbilityController {

    private final AbilityService abilityService;

    public AbilityController(AbilityService abilityService) {
        this.abilityService = abilityService;
    }

    @PostMapping
    public ResponseEntity<Void> createAbility(@AuthMemberPrincipal Member member, @RequestBody AbilityRequest abilityRequest) {
        abilityService.createAbility(member, abilityRequest);

        return ResponseEntity.status(CREATED).build();
    }
}
