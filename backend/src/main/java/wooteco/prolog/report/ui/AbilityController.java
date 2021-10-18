package wooteco.prolog.report.ui;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.login.aop.MemberOnly;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.ui.LoginMember;
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

    @MemberOnly
    @PostMapping("/abilities")
    public ResponseEntity<Void> createAbility(@AuthMemberPrincipal LoginMember member, @RequestBody AbilityCreateRequest abilityCreateRequest) {
        abilityService.createAbility(member.getId(), abilityCreateRequest);

        return ResponseEntity.ok().build();
    }

    @MemberOnly
    @PostMapping("/abilities/template/{template}")
    public ResponseEntity<Void> createBackendDefaultAbilities(@AuthMemberPrincipal LoginMember member,
                                                              @PathVariable String template) {
        abilityService.createTemplateAbilities(member.getId(), template);

        return ResponseEntity.ok().build();
    }

    @MemberOnly
    @GetMapping("/abilities")
    public ResponseEntity<List<AbilityResponse>> findAbilitiesByMember(@AuthMemberPrincipal LoginMember member) {
        return ResponseEntity.ok(abilityService.findAbilitiesByMemberId(member.getId()));
    }

    @MemberOnly
    @GetMapping("/abilities/parent-only")
    public ResponseEntity<List<AbilityResponse>> findParentAbilitiesByMember(@AuthMemberPrincipal LoginMember member) {
        return ResponseEntity.ok(abilityService.findParentAbilitiesByMemberId(member.getId()));
    }

    @MemberOnly
    @GetMapping("/members/{username}/abilities")
    public ResponseEntity<List<AbilityResponse>> findAbilitiesByUsername(@AuthMemberPrincipal LoginMember member,
                                                                         @PathVariable String username) {
        return ResponseEntity.ok(abilityService.findAbilitiesByMemberUsername(username));
    }

    @MemberOnly
    @PutMapping("/abilities/{abilityId}")
    public ResponseEntity<List<AbilityResponse>> updateAbility(@AuthMemberPrincipal LoginMember member,
                                                               @RequestBody AbilityUpdateRequest abilityUpdateRequest) {
        return ResponseEntity.ok(abilityService.updateAbility(member.getId(), abilityUpdateRequest));
    }

    @MemberOnly
    @DeleteMapping("/abilities/{abilityId}")
    public ResponseEntity<Void> deleteAbility(@AuthMemberPrincipal LoginMember member, @PathVariable Long abilityId) {
        abilityService.deleteAbility(member.getId(), abilityId);

        return ResponseEntity.ok().build();
    }
}
