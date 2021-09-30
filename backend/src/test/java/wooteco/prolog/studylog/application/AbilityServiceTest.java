package wooteco.prolog.report.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
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
        assertThatThrownBy(() -> abilityService.createAbility(member, abilityCreateRequest))
            .isExactlyInstanceOf(AbilityNotFoundException.class);
    }

    @DisplayName("부모 역량만을 조회한다. (자식 정보가 포함되지 않는다.)")
    @Test
    void parentAbilities() {
        // given
        Ability parentAbility = abilityRepository.save(Ability.parent("메타버스", "폴리곤 덩어리들", "123456", member));
        Ability childAbility = abilityRepository.save(Ability.child("마자용", "마자아아아~용", "하늘색", parentAbility, member));

        AbilityResponse expectResponse = new AbilityResponse(
            parentAbility.getId(),
            parentAbility.getName(),
            parentAbility.getDescription(),
            parentAbility.getColor(),
            parentAbility.isParent(),
            new ArrayList<>()
        );

        // when
        List<AbilityResponse> responses = abilityService.parentAbilities(member);

        // then
        assertThat(responses).hasSize(1);
        assertThat(responses.get(0)).usingRecursiveComparison()
            .isEqualTo(expectResponse);
    }

    @DisplayName("부모 역량 삭제에 성공하면 Member와 관계도 끊어진다.")
    @Test
    void deleteParentAbility() {
        // given
        Ability ability = abilityRepository.save(Ability.parent("zi존브라운123", "이견 있습니까?", "이견을 피로 물들이는 붉은 색", member));

        // when
        assertThat(member.getAbilities()).containsExactly(ability);
        abilityService.deleteAbility(member, ability.getId());

        // then
        assertThat(member.getAbilities()).isEmpty();
    }

    @DisplayName("부모 역량 삭제 시도시 자식역량이 존재하면 예외가 발생한다.")
    @Test
    void deleteParentAbilityException() {
        // given
        AbilityCreateRequest parentCreateRequest = new AbilityCreateRequest("zi존브라운123", "이견 있습니까?", "이견을 피로 물들이는 붉은 색", null);
        abilityService.createAbility(member, parentCreateRequest);
        Ability parentAbility = abilityRepository.findByMemberAndParentIsNull(member).iterator().next();

        AbilityCreateRequest childCreateRequest = new AbilityCreateRequest("손너잘", "내안으어두미", "크아아아악!!", parentAbility.getId());
        abilityService.createAbility(member, childCreateRequest);

        // when
        assertThat(member.getAbilities()).hasSize(2);

        // then
        assertThatThrownBy(() -> abilityService.deleteAbility(member, parentAbility.getId()))
            .isExactlyInstanceOf(AbilityHasChildrenException.class);
    }

    @DisplayName("자식 역량 삭제에 성공하면 부모 역량, Member와 관계도 끊어진다.")
    @Test
    void deleteChildAbility() {
        // given
        Ability parentAbility = abilityRepository.save(Ability.parent("zi존브라운123", "이견 있습니까?", "이견을 피로 물들이는 붉은 색", member));
        Ability childAbility = abilityRepository.save(Ability.child("손너잘", "내안으어두미", "크아아아악!!", parentAbility, member));

        // when
        assertThat(parentAbility.getChildren()).containsExactly(childAbility);
        assertThat(member.getAbilities()).containsExactly(parentAbility, childAbility);

        abilityService.deleteAbility(member, childAbility.getId());
        List<AbilityResponse> abilityResponses = abilityService.abilities(member);
        List<Long> abilityIds = abilityResponses.stream().map(AbilityResponse::getId).collect(Collectors.toList());

        // then
        assertThat(member.getAbilities()).containsExactly(parentAbility);
        assertThat(abilityIds).containsExactly(parentAbility.getId());
    }
}
