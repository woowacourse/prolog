package wooteco.prolog.report.ui;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
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
import wooteco.prolog.report.application.AbilityService;
import wooteco.prolog.report.application.HistoryAbilityService;
import wooteco.prolog.report.application.dto.Ability2.HistoryRequest;
import wooteco.prolog.report.application.dto.Ability2.HistoryResponse;
import wooteco.prolog.report.application.dto.ability.AbilityCreateRequest;
import wooteco.prolog.report.application.dto.ability.AbilityResponse;
import wooteco.prolog.report.application.dto.ability.AbilityUpdateRequest;
import wooteco.prolog.report.application.dto.ability.DefaultAbilityCreateRequest;

@RestController
@RequiredArgsConstructor
public class AbilityController {

    private final AbilityService abilityService;
    private final HistoryAbilityService historyAbilityService;

    @MemberOnly
    @PutMapping("/abilities")
    public ResponseEntity<HistoryResponse> update(@AuthMemberPrincipal LoginMember member,
                                                  @RequestBody HistoryRequest historyRequest) {
        HistoryResponse response = historyAbilityService.update(historyRequest, member.getId());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/members/{username}/abilities")
    public ResponseEntity<HistoryResponse> readLatestHistory(@PathVariable String username) {
        HistoryResponse historyResponse = historyAbilityService.readLatestHistoryByUsername(username);

        return ResponseEntity.ok(historyResponse);
    }

    @MemberOnly
    @PostMapping("/abilities/template/{template}")
    public ResponseEntity<Void> addDefaultAbilities(@AuthMemberPrincipal LoginMember member,
                                                    @PathVariable String template) {
        abilityService.addDefaultAbilities(member.getId(), template);

        return ResponseEntity.ok().build();
    }

    @Deprecated
    @MemberOnly
    @PostMapping("/abilities")
    public ResponseEntity<Void> createAbility(@AuthMemberPrincipal LoginMember member,
                                              @RequestBody AbilityCreateRequest abilityCreateRequest) {
        abilityService.createAbility(member.getId(), abilityCreateRequest);

        return ResponseEntity.ok().build();
    }

    @Deprecated
    @MemberOnly
    @PostMapping("/abilities/default")
    public ResponseEntity<Void> createDefaultAbilities(@AuthMemberPrincipal LoginMember member,
                                                       @RequestBody DefaultAbilityCreateRequest request) {
        abilityService.createDefaultAbility(request);

        return ResponseEntity.ok().build();
    }

    @Deprecated
    @MemberOnly
    @GetMapping("/abilities")
    public ResponseEntity<List<AbilityResponse>> findAbilitiesByMember(@AuthMemberPrincipal LoginMember member) {
        return ResponseEntity.ok(abilityService.findAbilitiesByMemberId(member.getId()));
    }

    @Deprecated
    @MemberOnly
    @GetMapping("/abilities/parent-only")
    public ResponseEntity<List<AbilityResponse>> findParentAbilitiesByMember(@AuthMemberPrincipal LoginMember member) {
        return ResponseEntity.ok(abilityService.findParentAbilitiesByMemberId(member.getId()));
    }

//    @Deprecated
//    @MemberOnly
//    @GetMapping("/members/{username}/abilities")
//    public ResponseEntity<List<AbilityResponse>> findAbilitiesByUsername(@AuthMemberPrincipal LoginMember member,
//                                                                         @PathVariable String username) {
//        return ResponseEntity.ok(abilityService.findAbilitiesByMemberUsername(username));
//    }

    @Deprecated
    @MemberOnly
    @PutMapping("/abilities/{abilityId}")
    public ResponseEntity<List<AbilityResponse>> updateAbility(@AuthMemberPrincipal LoginMember member,
                                                               @RequestBody AbilityUpdateRequest abilityUpdateRequest) {
        return ResponseEntity.ok(abilityService.updateAbility(member.getId(), abilityUpdateRequest));
    }

    @Deprecated
    @MemberOnly
    @DeleteMapping("/abilities/{abilityId}")
    public ResponseEntity<Void> deleteAbility(@AuthMemberPrincipal LoginMember member, @PathVariable Long abilityId) {
        abilityService.deleteAbility(member.getId(), abilityId);

        return ResponseEntity.ok().build();
    }
}
