package wooteco.prolog.member.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import wooteco.prolog.login.application.dto.GithubProfileResponse;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.member.application.dto.MemberUpdateRequest;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.member.exception.MemberNotFoundException;
import wooteco.support.utils.IntegrationTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@IntegrationTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("Member 조회 성공시 정보를 가져오고, 실패시 Member를 생성한다.")
    @Test
    void findOrCreateMemberTest() {
        // given
        GithubProfileResponse brownResponse = new GithubProfileResponse("브라운", "gracefulBrown", "1",
                "imageUrl1");
        GithubProfileResponse jasonResponse = new GithubProfileResponse("제이슨", "pjs", "2",
                "imageUrl2");

        Member를_생성한다(brownResponse.toMember());

        // when
        Member brown = memberService.findOrCreateMember(brownResponse);
        Member jason = memberService.findOrCreateMember(jasonResponse);

        // then
        assertThat(memberRepository.findById(brown.getId())).isPresent();
        assertThat(memberRepository.findById(jason.getId())).isPresent();
    }

    @DisplayName("ID를 통해서 Member를 조회한다.")
    @Test
    void findByIdTest() {
        // given
        Member savedMember = Member를_생성한다(
                new Member("gracefulBrown", "브라운", Role.CREW, 1L, "imageUrl"));

        // when
        Member foundMember = memberService.findById(savedMember.getId());

        // then
        assertThat(foundMember).usingRecursiveComparison()
                .isEqualTo(savedMember);
    }

    @DisplayName("ID를 통해서 Member 조회 실패시 지정된 예외가 발생한다.")
    @Test
    void findByIdExceptionTest() {
        // when, then
        assertThatThrownBy(() -> memberService.findById(999L))
                .isExactlyInstanceOf(MemberNotFoundException.class);
    }

    @DisplayName("Username을 통해서 Member를 조회한다.")
    @Test
    void findByUsernameTest() {
        // given
        Member savedMember = Member를_생성한다(
                new Member("gracefulBrown", "브라운", Role.CREW, 1L, "imageUrl"));

        // when
        Member foundMember = memberService.findByUsername(savedMember.getUsername());

        // then
        assertThat(foundMember).usingRecursiveComparison()
                .isEqualTo(savedMember);
    }

    @DisplayName("Username를 통해서 Member 조회 실패시 지정된 예외가 발생한다.")
    @Test
    void findByUsernameExceptionTest() {
        // when, then
        assertThatThrownBy(() -> memberService.findByUsername("이 세상에 존재할 수 없는 이름"))
                .isExactlyInstanceOf(MemberNotFoundException.class);
    }

    @DisplayName("Username을 통해서 MemberResponse를 조회한다.")
    @Test
    void findMemberResponseByUsernameTest() {
        // given
        Member savedMember = Member를_생성한다(
                new Member("gracefulBrown", "브라운", Role.CREW, 1L, "imageUrl"));
        MemberResponse expectMemberResponse = MemberResponse.of(savedMember);

        // when
        MemberResponse foundMemberResponse = memberService
                .findMemberResponseByUsername(savedMember.getUsername());

        // then
        assertThat(foundMemberResponse).usingRecursiveComparison()
                .isEqualTo(expectMemberResponse);
    }

    @DisplayName("Username를 통해서 MemberResponse 조회 실패시 지정된 예외가 발생한다.")
    @Test
    void findMemberResponseByUsernameExceptionTest() {
        // when, then
        assertThatThrownBy(() -> memberService.findMemberResponseByUsername("이 세상에 존재할 수 없는 이름"))
                .isExactlyInstanceOf(MemberNotFoundException.class);
    }

    @DisplayName("Member 정보를 업데이트 한다.")
    @Test
    void updateMemberTest() {
        // given
        String 기존_닉네임 = "브라운";
        String 기존_이미지 = "imageUrl";
        String 새로운_닉네임 = "브라운2세";
        String 새로운_이미지 = "superPowerImageUrl";

        Member savedMember = Member를_생성한다(
                new Member("gracefulBrown", 기존_닉네임, Role.CREW, 1L, 기존_이미지));
        MemberUpdateRequest updateRequest = new MemberUpdateRequest(새로운_닉네임, 새로운_이미지);

        // when
        memberService.updateMember(savedMember, updateRequest);

        // then
        Member foundMember = memberService.findById(savedMember.getId());

        assertThat(foundMember.getNickname()).isEqualTo(새로운_닉네임);
        assertThat(foundMember.getImageUrl()).isEqualTo(새로운_이미지);
    }

    @DisplayName("존재하지 않는 Member 정보 업데이트 시도시 예외가 발생한다.")
    @Test
    void updateMemberNotFoundExceptionTest() {
        // given
        String 기존_닉네임 = "브라운";
        String 기존_이미지 = "imageUrl";
        String 새로운_닉네임 = "브라운2세";
        String 새로운_이미지 = "superPowerImageUrl";

        Member member = new Member("gracefulBrown", 기존_닉네임, Role.CREW, 1L, 기존_이미지);
        MemberUpdateRequest updateRequest = new MemberUpdateRequest(새로운_닉네임, 새로운_이미지);

        // when, then
        assertThatThrownBy(() -> memberService.updateMember(member, updateRequest))
                .isExactlyInstanceOf(MemberNotFoundException.class);
    }

    private Member Member를_생성한다(Member member) {
        return memberRepository.save(member);
    }

}