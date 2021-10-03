package wooteco.prolog.report.ui;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.report.application.AbilityService;
import wooteco.prolog.report.application.dto.ability.AbilityCreateRequest;
import wooteco.prolog.report.application.dto.ability.AbilityResponse;
import wooteco.prolog.report.application.dto.ability.AbilityUpdateRequest;

@RestController
public class AbilityController {

    private final AbilityService abilityService;

    public AbilityController(AbilityService abilityService) {
        this.abilityService = abilityService;
    }

    @PostMapping("/abilities")
    public ResponseEntity<Void> createAbility(@AuthMemberPrincipal Member member, @RequestBody AbilityCreateRequest abilityCreateRequest) {
        abilityService.createAbility(member, abilityCreateRequest);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/abilities")
    public ResponseEntity<List<AbilityResponse>> findAbilitiesByMember(@AuthMemberPrincipal Member member) {
        return ResponseEntity.ok(abilityService.findAbilitiesByMember(member));
    }

    @GetMapping("/abilities/parent-only")
    public ResponseEntity<List<AbilityResponse>> findParentAbilitiesByMember(@AuthMemberPrincipal Member member) {
        return ResponseEntity.ok(abilityService.findParentAbilitiesByMember(member));
    }

    @GetMapping("/members/{memberId}/abilities")
    public ResponseEntity<List<AbilityResponse>> findAbilitiesByMemberId(@AuthMemberPrincipal Member member, @PathVariable Long memberId) {
        return ResponseEntity.ok(abilityService.findAbilitiesByMemberId(memberId));
    }

    @PutMapping("/abilities/{abilityId}")
    public ResponseEntity<List<AbilityResponse>> updateAbility(@AuthMemberPrincipal Member member,
                                                               @RequestBody AbilityUpdateRequest abilityUpdateRequest) {
        return ResponseEntity.ok(abilityService.updateAbility(member, abilityUpdateRequest));
    }

    @DeleteMapping("/abilities/{abilityId}")
    public ResponseEntity<Void> deleteAbility(@AuthMemberPrincipal Member member, @PathVariable Long abilityId) {
        abilityService.deleteAbility(member, abilityId);

        return ResponseEntity.ok().build();
    }
}
