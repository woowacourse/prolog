package wooteco.prolog.ability.ui;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.ability.application.StudylogAbilityService;
import wooteco.prolog.ability.application.dto.AbilityResponse;
import wooteco.prolog.ability.application.dto.AbilityStudylogResponse;
import wooteco.prolog.ability.application.dto.StudylogAbilityRequest;
import wooteco.prolog.login.aop.MemberOnly;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.ui.LoginMember;

@RestController
public class StudylogAbilityController {

    private StudylogAbilityService studylogAbilityService;

    public StudylogAbilityController(StudylogAbilityService studylogAbilityService) {
        this.studylogAbilityService = studylogAbilityService;
    }

    @MemberOnly
    @PutMapping("/studylogs/{studylogId}/abilities")
    public ResponseEntity<List<AbilityResponse>> updateStudylogAbilities(@AuthMemberPrincipal LoginMember member,
                                                                         @PathVariable Long studylogId,
                                                                         @RequestBody StudylogAbilityRequest studylogAbilityRequest) {
        List<AbilityResponse> abilityResponses = studylogAbilityService.updateStudylogAbilities(member.getId(), studylogId, studylogAbilityRequest);
        return ResponseEntity.ok().body(abilityResponses);
    }

    @GetMapping("/members/{username}/ability-studylogs")
    public ResponseEntity<List<AbilityStudylogResponse>> findAbilityStudylogs(@PathVariable String username,
                                                                              @RequestParam(required = false) List<Long> abilityIds,
                                                                              @PageableDefault(size = 5, direction = Direction.DESC, sort = "id") Pageable pageable) {
        List<AbilityStudylogResponse> studylogs = studylogAbilityService.findAbilityStudylogsByAbilityIds(username, abilityIds, pageable);
        return ResponseEntity.ok().body(studylogs);
    }

    @GetMapping("/members/{username}/ability-studylogs/mapping-only")
    public ResponseEntity<List<AbilityStudylogResponse>> findAbilityStudylogsMappingOnly(@PathVariable String username,
                                                                                         @RequestParam(required = false) List<Long> abilityIds,
                                                                                         @PageableDefault(size = 5, direction = Direction.DESC, sort = "id") Pageable pageable) {
        List<AbilityStudylogResponse> studylogs = studylogAbilityService.findAbilityStudylogsMappingOnlyByAbilityIds(username, abilityIds, pageable);
        return ResponseEntity.ok().body(studylogs);
    }
}
