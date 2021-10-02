package wooteco.prolog.studylog.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.report.application.AbilityService;
import wooteco.prolog.report.application.dto.ability.AbilityCreateRequest;
import wooteco.prolog.report.application.dto.ability.AbilityResponse;
import wooteco.prolog.report.domain.ablity.Ability;
import wooteco.prolog.report.domain.ablity.repository.AbilityRepository;
import wooteco.prolog.report.exception.AbilityHasChildrenException;
import wooteco.prolog.report.exception.AbilityNotFoundException;
import wooteco.prolog.report.exception.AbilityParentChildColorDifferentException;
import wooteco.prolog.studylog.exception.AbilityNameDuplicateException;
import wooteco.prolog.studylog.exception.AbilityParentColorDuplicateException;
import wooteco.support.utils.IntegrationTest;

@IntegrationTest
class AbilityServiceTest {

    @Autowired
    private AbilityService abilityService;

    @Autowired
    private AbilityRepository abilityRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Member member;

    @BeforeEach
    void setUp() {
        member = memberRepository.save(new Member("brown", "브라운", Role.CREW, 1L, "imageUrl"));
    }

    @DisplayName("역량을 생성 할 때 부모역량 ID와 일치하는 역량이 없으면 예외가 발생한다.")
    @Test
    void createAbilityException() {
        // given
        AbilityCreateRequest abilityCreateRequest = new AbilityCreateRequest("zi존브라운123", "이견 있습니까?", "이견을 피로 물들이는 붉은 색", Long.MAX_VALUE);

        // when, then
        assertThatThrownBy(() -> abilityService.createAbility(member.getId(), abilityCreateRequest))
            .isExactlyInstanceOf(AbilityNotFoundException.class);
    }

    @DisplayName("역량을 생성할 때 기존에 존재하는 역량과 이름이 같은 경우 예외가 발생한다.")
    @Test
    void createAbilityNameDuplicateException() {
        // given
        String name = "zi존브라운123";
        AbilityCreateRequest request1 = new AbilityCreateRequest(name, "이견 있습니까?", "이견을 피로 물들이는 붉은 색", null);

        abilityService.createAbility(member.getId(), request1);

        // when, then
        AbilityCreateRequest request2 = new AbilityCreateRequest(name, "없어용", "침묵의 검은 색", null);
        assertThatThrownBy(() -> abilityService.createAbility(member.getId(), request2))
            .isExactlyInstanceOf(AbilityNameDuplicateException.class);
    }

    @DisplayName("부모 역량을 생성할 때 기존에 존재하는 다른 역량들과 색상이 같은 경우 예외가 발생한다.")
    @Test
    void createParentAbilityColorDuplicateException() {
        // given
        String color = "이견을 피로 물들이는 붉은 색";
        AbilityCreateRequest request1 = new AbilityCreateRequest("zi존브라운123", "이견 있습니까?", color, null);

        abilityService.createAbility(member.getId(), request1);

        // when, then
        AbilityCreateRequest request2 = new AbilityCreateRequest("그냥막구현해", "없어용", color, null);
        assertThatThrownBy(() -> abilityService.createAbility(member.getId(), request2))
            .isExactlyInstanceOf(AbilityParentColorDuplicateException.class);
    }

    @DisplayName("자식 역량을 생성할 때 부모 역량과 색상이 다른 경우 예외가 발생한다.")
    @Test
    void createChildAbilityColorDifferentException() {
        // given
        AbilityCreateRequest parentAbilityRequest = new AbilityCreateRequest("zi존브라운123", "이견 있습니까?", "이견을 피로 물들이는 붉은 색", null);

        abilityService.createAbility(member.getId(), parentAbilityRequest);
        Long parentId = abilityService.findAbilitiesByMemberId(member.getId()).iterator().next().getId();

        // when, then
        AbilityCreateRequest childAbilityRequest = new AbilityCreateRequest("그냥막구현해", "없어용", "검은 색 이에용", parentId);
        assertThatThrownBy(() -> abilityService.createAbility(member.getId(), childAbilityRequest))
            .isExactlyInstanceOf(AbilityParentChildColorDifferentException.class);
    }

    @DisplayName("부모 역량만을 조회한다. (자식 정보가 포함되지 않는다.)")
    @Test
    void parentAbilities() {
        // given
        Ability parentAbility = abilityRepository.save(Ability.parent("메타버스", "폴리곤 덩어리들", "123456", member));
        Ability childAbility = abilityRepository.save(Ability.child("마자용", "마자아아아~용", "123456", parentAbility, member));

        AbilityResponse expectResponse = new AbilityResponse(
            parentAbility.getId(),
            parentAbility.getName(),
            parentAbility.getDescription(),
            parentAbility.getColor(),
            parentAbility.isParent(),
            new ArrayList<>()
        );

        // when
        List<AbilityResponse> responses = abilityService.findParentAbilitiesByMemberId(member.getId());

        // then
        assertThat(responses).hasSize(1);
        assertThat(responses.get(0)).usingRecursiveComparison()
            .isEqualTo(expectResponse);
    }

    @DisplayName("부모 역량 삭제에 성공하면 조회되지 않는다.")
    @Test
    void deleteParentAbility() {
        // given
        Ability ability = abilityRepository.save(Ability.parent("zi존브라운123", "이견 있습니까?", "이견을 피로 물들이는 붉은 색", member));

        // when
        assertThat(abilityService.findAbilitiesByMemberId(member.getId())).usingRecursiveComparison()
            .isEqualTo(AbilityResponse.of(Collections.singletonList(ability)));

        abilityService.deleteAbility(member.getId(), ability.getId());

        // then
        assertThat(abilityService.findAbilitiesByMemberId(member.getId())).isEmpty();
    }

    @DisplayName("부모 역량 삭제 시도시 자식역량이 존재하면 예외가 발생한다.")
    @Test
    void deleteParentAbilityException() {
        // given
        AbilityCreateRequest parentCreateRequest = new AbilityCreateRequest("zi존브라운123", "이견 있습니까?", "이견을 피로 물들이는 붉은 색", null);
        abilityService.createAbility(member.getId(), parentCreateRequest);
        Ability parentAbility = abilityRepository.findByMemberIdAndParentIsNull(member.getId()).iterator().next();

        AbilityCreateRequest childCreateRequest = new AbilityCreateRequest("손너잘", "내안으어두미", "이견을 피로 물들이는 붉은 색", parentAbility.getId());
        abilityService.createAbility(member.getId(), childCreateRequest);

        // when, then
        assertThatThrownBy(() -> abilityService.deleteAbility(member.getId(), parentAbility.getId()))
            .isExactlyInstanceOf(AbilityHasChildrenException.class);
    }

    @DisplayName("자식 역량 삭제에 성공하면 부모 역량과 관계가 끊어진다.")
    @Test
    void deleteChildAbility() {
        // given
        Ability parentAbility = abilityRepository.save(Ability.parent("zi존브라운123", "이견 있습니까?", "이견을 피로 물들이는 붉은 색", member));
        Ability childAbility = abilityRepository.save(Ability.child("손너잘", "내안으어두미", "이견을 피로 물들이는 붉은 색", parentAbility, member));

        // when
        assertThat(parentAbility.getChildren()).containsExactly(childAbility);

        abilityService.deleteAbility(member.getId(), childAbility.getId());
        List<AbilityResponse> abilityResponses = abilityService.findAbilitiesByMemberId(member.getId());
        List<Long> abilityIds = abilityResponses.stream().map(AbilityResponse::getId).collect(Collectors.toList());

        // then
        assertThat(abilityIds).containsExactly(parentAbility.getId());
    }

    @DisplayName("memberId를 통해 해당 멤버의 역량목록을 조회한다.")
    @Test
    void findMemberAbilitiesByMemberId() {
        // given
        Ability ability1 = abilityRepository.save(Ability.parent("나는ZI존브라운이다.", "이견있나?", "피의 붉은 색", member));
        Ability ability2 = abilityRepository.save(Ability.parent("나는킹갓브라운이다.", "이견은 없겠지?", "무지개 색", member));

        // when
        List<AbilityResponse> abilityResponses = abilityService.findAbilitiesByMemberId(member.getId());

        // then
        assertThat(abilityResponses).usingRecursiveComparison()
            .isEqualTo(AbilityResponse.of(Arrays.asList(ability1, ability2)));
    }
}
