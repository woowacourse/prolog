package wooteco.prolog.member.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;

@DataJpaTest
class MemberRepositoryTest {

    private static final Member 웨지 = new Member("sihyung92", "웨지", Role.CREW, 2222L, "https://avatars.githubusercontent.com/u/51393021?v=4");

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("Member 생성")
    @Test
    void createMember() {
        // given
        LocalDateTime beforeTime = LocalDateTime.now();

        // when
        Member savedMember = memberRepository.save(웨지);

        // then
        assertThat(savedMember.getId()).isNotNull();
        assertThat(savedMember.getCreatedAt()).isAfterOrEqualTo(beforeTime);
        assertThat(savedMember).usingRecursiveComparison()
            .isEqualTo(웨지);
    }

    @DisplayName("githubId로 Member 검색 - 성공")
    @Test
    void findByGithubId() {
        // given
        Member member = 멤버를_생성한다(웨지);

        // when
        Optional<Member> foundMember = memberRepository.findByGithubId(웨지.getGithubId());

        // then
        assertThat(foundMember).isPresent();
        assertThat(foundMember.get()).usingRecursiveComparison()
            .isEqualTo(member);
    }

    @DisplayName("githubId로 Member 검색 - 실패")
    @Test
    void findByGithubIdFailed() {
        // when
        Optional<Member> foundMember = memberRepository.findByGithubId(웨지.getGithubId());

        // then
        assertThat(foundMember).isNotPresent();
    }

    @DisplayName("username으로 Member 검색 - 성공")
    @Test
    void findByUsername() {
        // given
        Member member = 멤버를_생성한다(웨지);

        // when
        Optional<Member> foundMember = memberRepository.findByUsername(웨지.getUsername());

        // then
        assertThat(foundMember).isPresent();
        assertThat(foundMember.get()).usingRecursiveComparison()
            .isEqualTo(member);
    }

    @DisplayName("username으로 Member 검색 - 실패")
    @Test
    void findByUsernameFailed() {
        // when
        Optional<Member> foundMember = memberRepository.findByUsername(웨지.getUsername());

        // then
        assertThat(foundMember).isNotPresent();
    }

    private Member 멤버를_생성한다(Member member) {
        return memberRepository.save(member);
    }
}