package wooteco.prolog.studylog.ui;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.studylog.application.AbilityService;
import wooteco.prolog.studylog.application.dto.ability.AbilityCreateRequest;
import wooteco.prolog.studylog.application.dto.ability.AbilityResponse;
import wooteco.prolog.studylog.application.dto.ability.AbilityUpdateRequest;

@RestController
@RequestMapping("/abilities")
public class AbilityController {

    private final AbilityService abilityService;

    public AbilityController(AbilityService abilityService) {
        this.abilityService = abilityService;
    }

    @PostMapping
    public ResponseEntity<Void> createAbility(@AuthMemberPrincipal Member member, @RequestBody AbilityCreateRequest abilityCreateRequest) {
        abilityService.createAbility(member, abilityCreateRequest);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<AbilityResponse>> abilities(@AuthMemberPrincipal Member member) {
        return ResponseEntity.ok(abilityService.abilities(member));
    }

    @GetMapping("parent-only")
    public ResponseEntity<List<AbilityResponse>> parentAbilities(@AuthMemberPrincipal Member member) {
        return ResponseEntity.ok(abilityService.parentAbilities(member));
    }

    @PutMapping("/{abilityId}")
    public ResponseEntity<List<AbilityResponse>> updateAbility(@AuthMemberPrincipal Member member,
                                                               @RequestBody AbilityUpdateRequest abilityUpdateRequest) {
        return ResponseEntity.ok(abilityService.updateAbility(member, abilityUpdateRequest));
    }

    @DeleteMapping("/{abilityId}")
    public ResponseEntity<Void> deleteAbility(@AuthMemberPrincipal Member member, @PathVariable Long abilityId) {
        abilityService.deleteAbility(member, abilityId);

        return ResponseEntity.ok().build();
    }
}
