package wooteco.prolog.member.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import wooteco.prolog.studylog.domain.Tag;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

class MemberTagTest {

    private static Member bebe = new Member(1L, "최원용", "베베", Role.CREW, 1L, "test");
    private static Tag tag = new Tag(1L, "#베베바보");
    private static MemberTag memberTag = new MemberTag(bebe, tag);

    @DisplayName("addCount를 수행하면 count가 증가한다.")
    @Test
    void addCount() {
        //given
        int count = memberTag.getCount();
        //when
        memberTag.addCount();
        int actual = memberTag.getCount();
        //then
        Assertions.assertThat(actual).isEqualTo(count + 1);
    }

    @DisplayName("decreaseCount를 수행하면 count가 감소한다.")
    @Test
    void decreaseCount() {
        //given
        int count = memberTag.getCount();
        //when
        memberTag.decreaseCount();
        int actual = memberTag.getCount();
        //then
        Assertions.assertThat(actual).isEqualTo(count - 1);
    }

    @DisplayName("MemberTag를 생성하면 count는 1이다")
    @Test
    void hasOnlyOne() {
        Assertions.assertThat(memberTag.hasOnlyOne()).isTrue();
    }

    @DisplayName("Member와 Tag 이름이 같으면 같은 MemberTag이다")
    @ParameterizedTest(name = "{index} : 로또 번호 비교 테스트 (비교 결과 == {1})")
    @MethodSource("parametersProvider1")
    void isSameTagWhenSameMember(Tag otherTag, Member otherMember, boolean expected) {
        //given
        MemberTag otherMemberTag = new MemberTag(otherMember, otherTag);
        //when
        boolean actual = memberTag.isSame(otherMemberTag);
        //then
        Assertions.assertThat(actual).isEqualTo(expected);
    }


    static Stream<Arguments> parametersProvider1() {
        Tag otherTag = new Tag(2L, "#다른태그");
        Member hoy = new Member(2L, "이건호", "호이", Role.CREW, 2L, "imageUrl");
        return Stream.of(
            arguments(tag, bebe, true),
            arguments(tag, hoy, false),
            arguments(otherTag, hoy, false)
        );
    }

    @DisplayName("equals 재정의 테스트")
    @ParameterizedTest(name = "{index}: 예상 비교 결과 == {1}")
    @MethodSource("parametersProvider2")
    void MemberTagTest(Object otherMemberTag, boolean expected) {
        boolean actual = memberTag.equals(otherMemberTag);
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> parametersProvider2() {
        Tag otherTag = new Tag(2L, "#다른태그");
        Member hoy = new Member(2L, "이건호", "호이", Role.CREW, 2L, "imageUrl");
        return Stream.of(
            arguments(null, false),
            arguments("otherClass", false),
            arguments(new MemberTag(bebe, otherTag), false),
            arguments(new MemberTag(hoy, tag), false),
            arguments(new MemberTag(hoy, otherTag), false),
            arguments(memberTag, true)
        );
    }
}
