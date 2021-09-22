package wooteco.prolog.studylog.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.studylog.application.dto.ability.AbilityCreateRequest;
import wooteco.prolog.studylog.application.dto.ability.AbilityResponse;
import wooteco.prolog.studylog.application.dto.ability.ChildAbilityDto;
import wooteco.prolog.studylog.domain.ablity.Ability;
import wooteco.prolog.studylog.domain.repository.AbilityRepository;
import wooteco.prolog.studylog.exception.AbilityNotFoundException;
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

    @DisplayName("역량을 삭제하면 Member와 관계도 끊어진다.")
    @Test
    void deleteAbility() {
        // given
        Ability ability = abilityRepository.save(Ability.parent("zi존브라운123", "이견 있습니까?", "이견을 피로 물들이는 붉은 색", member));

        // when
        assertThat(member.getAbilities()).containsExactly(ability);
        abilityService.deleteAbility(member, ability.getId());

        // then
        assertThat(member.getAbilities()).isEmpty();
    }
}