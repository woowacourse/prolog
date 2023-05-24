package wooteco.prolog.member.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.MemberTag;
import wooteco.prolog.member.domain.MemberTags;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.member.domain.repository.MemberTagRepository;
import wooteco.prolog.studylog.application.dto.MemberTagResponse;
import wooteco.prolog.studylog.domain.Tag;
import wooteco.prolog.studylog.domain.Tags;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;

@ExtendWith(MockitoExtension.class)
class MemberTagServiceTest {

    @Mock
    private MemberService memberService;
    @Mock
    private MemberTagRepository memberTagRepository;
    @Mock
    private StudylogRepository studylogRepository;
    @InjectMocks
    private MemberTagService memberTagService;

    @DisplayName("findByMember()를 호출할 때 해당 멤버가 사용한 태그를 취합하여 리스트로 반환한다")
    @Test
    void findByMember() {
        //given
        final String memberName = "송세연";
        final Member member = new Member(1L, memberName, "아마란스", Role.CREW, 124L, "imageUrl");
        List<Tag> tags = LongStream.range(1, 4).boxed().map(id -> new Tag(id, id.toString()))
            .collect(Collectors.toList());
        member.addTags(new Tags(tags));
        final int expectedStudylogCount = 4;
        when(memberService.findByUsername(memberName)).thenReturn(member);
        when(studylogRepository.countByMember(member)).thenReturn(expectedStudylogCount);

        //when
        List<MemberTagResponse> expected = memberTagService.findByMember(memberName);

        //then
        Assertions.assertAll(() -> assertThat(expected.size()).isEqualTo(4),
            () -> assertThat(expected.get(0).getCount()).isEqualTo(4),
            () -> assertThat(expected.get(0).getTagResponse().getName()).isEqualTo("ALL"),
            () -> IntStream.range(1, 3).boxed().forEach((i) -> {
                assertThat(expected.get(i).getCount()).isEqualTo(1);
                assertThat(expected.get(i).getTagResponse().getName()).isEqualTo(i.toString());
            }));
    }

    @DisplayName("tags를 memberTag로 바꾸고, 이를 저장한다.")
    @Test
    void registerMemberTag() {
        //given
        final Member member = new Member(1L, "홍혁준", "홍실", Role.CREW, 123L, "imageUrl");

        final Tag javaTag = new Tag(1L, "자바");
        final Tag springTag = new Tag(2L, "스프링");
        final Tag aopTag = new Tag(3L, "AOP");
        final Tags tags = new Tags(Arrays.asList(javaTag, springTag, aopTag));

        final ArgumentCaptor<MemberTags> argumentCaptor = ArgumentCaptor.forClass(MemberTags.class);

        //when
        memberTagService.registerMemberTag(tags, member);

        //then
        verify(memberTagRepository).register(argumentCaptor.capture());
        final MemberTags memberTags = argumentCaptor.getValue();
        assertThat(memberTags.getValues())
            .extracting(MemberTag::getMemberId, MemberTag::getTagId)
            .containsExactlyInAnyOrder(
                tuple(member.getId(), javaTag.getId()),
                tuple(member.getId(), springTag.getId()),
                tuple(member.getId(), aopTag.getId())
            );
    }

    @DisplayName("updateMemberTag()를 호출할 때 memberTagRepository의 특정 MemberTag가 변경된다")
    @Test
    void updateMemberTag() {
        //given
        final Member member = new Member(1L, "송세연", "아마란스", Role.CREW, 124L, "imageUrl");

        List<Tag> tags1 = LongStream.range(1, 4).boxed().map(id -> new Tag(id, id.toString()))
            .collect(Collectors.toList());
        Tags originTags = new Tags(tags1);

        List<Tag> tags2 = LongStream.range(5, 8).boxed().map(id -> new Tag(id, id.toString()))
            .collect(Collectors.toList());
        Tags newTags = new Tags(tags2);

        //when
        memberTagService.updateMemberTag(originTags, newTags, member);

        //then
        verify(memberTagRepository, times(1)).update(any(), any());
    }

    @DisplayName("tags를 memberTags로 바꾸고, 이를 제거한다.")
    @Test
    void removeMemberTag() {
        //given
        final Member member = new Member(1L, "홍혁준", "홍실", Role.CREW, 123L, "imageUrl");

        final Tag javaTag = new Tag(1L, "자바");
        final Tag springTag = new Tag(2L, "스프링");
        final Tag aopTag = new Tag(3L, "AOP");
        final Tags tags = new Tags(Arrays.asList(javaTag, springTag, aopTag));
        final ArgumentCaptor<MemberTags> argumentCaptor = ArgumentCaptor.forClass(MemberTags.class);

        //when
        memberTagService.removeMemberTag(tags, member);

        //then
        verify(memberTagRepository).unregister(argumentCaptor.capture());
        final MemberTags memberTags = argumentCaptor.getValue();
        assertThat(memberTags.getValues())
            .extracting(MemberTag::getMemberId, MemberTag::getTagId)
            .containsExactlyInAnyOrder(
                tuple(member.getId(), javaTag.getId()),
                tuple(member.getId(), springTag.getId()),
                tuple(member.getId(), aopTag.getId())
            );
    }
}
