package wooteco.prolog.studylog.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.studylog.application.dto.ability.AbilityCreateRequest;
import wooteco.prolog.studylog.exception.AbilityNotFoundException;
import wooteco.support.utils.IntegrationTest;

@IntegrationTest
class AbilityServiceTest {

    @Autowired
    private AbilityService abilityService;

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
}