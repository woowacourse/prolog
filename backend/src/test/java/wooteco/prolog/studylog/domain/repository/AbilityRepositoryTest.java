package wooteco.prolog.studylog.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.studylog.domain.ablity.Ability;

@DataJpaTest
class AbilityRepositoryTest {

    @Autowired
    private AbilityRepository abilityRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        Member member = memberRepository.save(new Member("Hyeon9mak", "현구막", Role.CREW, 1L, "imageUrl"));
        abilityRepository.save(Ability.parent("손너잘", "손너잘은 3인칭을 쓴다.", "112233", member));
    }

    @DisplayName("findById에 null을 이용하면 예외가 발생한다.")
    @Test
    void findByIdNullException() {
        // given
        Long id = null;

        // when, then
        assertThatThrownBy(() -> abilityRepository.findById(id))
            .isExactlyInstanceOf(InvalidDataAccessApiUsageException.class);
    }

    @DisplayName("역량 ID와 Member를 통해 역량을 탐색할 때")
    @Nested
    class FindByIdAndMember {

        private Member savedMember;
        private Ability savedAbility;

        @BeforeEach
        void setUp() {
            savedMember = memberRepository.save(new Member("bperhaps", "손너잘", Role.CREW, 2L, "imageUrl"));
            savedAbility = abilityRepository.save(Ability.parent("메타버스", "폴리곤 덩어리들", "123456", savedMember));
        }

        @DisplayName("역량 ID와 Member가 일치하면 탐색에 성공한다.")
        @Test
        void findByIdAndMember() {
            // when
            Optional<Ability> foundAbility = abilityRepository.findByIdAndMember(savedAbility.getId(), savedMember);

            // then
            assertThat(foundAbility).isPresent();
        }

        @DisplayName("역량 ID가 일치하지 않을 경우 탐색에 실패한다.")
        @Test
        void findByIdAndMemberAbilityIdException() {
            // when
            Optional<Ability> foundAbility = abilityRepository.findByIdAndMember(Long.MAX_VALUE, savedMember);

            // then
            assertThat(foundAbility).isNotPresent();
        }

        @DisplayName("Member가 일치하지 않을 경우 탐색에 실패한다.")
        @Test
        void findByIdAndMemberMemberException() {
            // given
            Member anotherMember = memberRepository.save(new Member("seovalue", "조앤", Role.CREW, 3L, "imageUrl"));

            // when
            Optional<Ability> foundAbility = abilityRepository.findByIdAndMember(savedAbility.getId(), anotherMember);

            // then
            assertThat(foundAbility).isNotPresent();
        }
    }
}