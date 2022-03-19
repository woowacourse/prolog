package wooteco.prolog.ability.ui;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.login.aop.MemberOnly;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.ability.application.AbilityService;
import wooteco.prolog.ability.application.dto.AbilityCreateRequest;
import wooteco.prolog.ability.application.dto.AbilityResponse;
import wooteco.prolog.ability.application.dto.AbilityUpdateRequest;
import wooteco.prolog.ability.application.dto.DefaultAbilityCreateRequest;

@RestController
public class AbilityController {

    private final AbilityService abilityService;

    public AbilityController(AbilityService abilityService) {
        this.abilityService = abilityService;
    }

    @MemberOnly
    @PostMapping("/abilities/template/{template}")
    public ResponseEntity<Void> addDefaultAbilities(@AuthMemberPrincipal LoginMember member, @PathVariable String template) {
        abilityService.addDefaultAbilities(member.getId(), template);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/abilities/default")
    public ResponseEntity<Void> createDefaultAbilities(@RequestBody DefaultAbilityCreateRequest request) {
        abilityService.createDefaultAbility(request);

        return ResponseEntity.ok().build();
    }

    @MemberOnly
    @PostMapping("/abilities")
    public ResponseEntity<AbilityResponse> createAbility(@AuthMemberPrincipal LoginMember member, @RequestBody AbilityCreateRequest abilityCreateRequest) {
        AbilityResponse abilityResponse = abilityService.createAbility(member.getId(), abilityCreateRequest);
        return ResponseEntity.ok().body(abilityResponse);
    }

    @GetMapping("/members/{username}/abilities")
    public ResponseEntity<List<AbilityResponse>> findAbilitiesByUsername(@PathVariable String username) {
        return ResponseEntity.ok(abilityService.findAbilitiesByMemberUsername(username));
    }

    @MemberOnly
    @GetMapping("/abilities/parent-only")
    public ResponseEntity<List<AbilityResponse>> findParentAbilitiesByMember(@AuthMemberPrincipal LoginMember member) {
        return ResponseEntity.ok(abilityService.findParentAbilitiesByMemberId(member.getId()));
    }

    @MemberOnly
    @PutMapping("/abilities/{abilityId}")
    public ResponseEntity<List<AbilityResponse>> updateAbility(@AuthMemberPrincipal LoginMember member,
                                                               @PathVariable Long abilityId,
                                                               @RequestBody AbilityUpdateRequest abilityUpdateRequest) {
        return ResponseEntity.ok(abilityService.updateAbility(member.getId(), abilityId, abilityUpdateRequest));
    }

    @MemberOnly
    @DeleteMapping("/abilities/{abilityId}")
    public ResponseEntity<Void> deleteAbility(@AuthMemberPrincipal LoginMember member, @PathVariable Long abilityId) {
        abilityService.deleteAbility(member.getId(), abilityId);
        return ResponseEntity.ok().build();
    }
}
