package wooteco.prolog.studylog.domain.repository;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
}