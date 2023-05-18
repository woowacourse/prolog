package wooteco.prolog.member.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.mockito.Mockito.verify;

import org.elasticsearch.common.collect.List;
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

    @DisplayName("tags를 memberTag로 바꾸고, 이를 저장한다.")
    @Test
    void registerMemberTag() {
        //given
        final Member member = new Member(1L, "홍혁준", "홍실", Role.CREW, 123L, "imageUrl");

        final Tag javaTag = new Tag(1L, "자바");
        final Tag springTag = new Tag(2L, "스프링");
        final Tag aopTag = new Tag(3L, "AOP");
        final Tags tags = new Tags(List.of(javaTag, springTag, aopTag));

        final ArgumentCaptor<MemberTags> argumentCaptor = ArgumentCaptor.forClass(MemberTags.class);

        //when
        memberTagService.registerMemberTag(tags, member);

        //then
        verify(memberTagRepository).register(argumentCaptor.capture());
        final MemberTags memberTags = argumentCaptor.getValue();
        assertThat(memberTags.getValues()).extracting(MemberTag::getMemberId, MemberTag::getTagId)
            .containsExactlyInAnyOrder(
                tuple(member.getId(), javaTag.getId()),
                tuple(member.getId(), springTag.getId()),
                tuple(member.getId(), aopTag.getId())
            );
    }

    @DisplayName("tags를 memberTags로 바꾸고, 이를 제거한다.")
    @Test
    void removeMemberTag() {
        //given
        final Member member = new Member(1L, "홍혁준", "홍실", Role.CREW, 123L, "imageUrl");

        final Tag javaTag = new Tag(1L, "자바");
        final Tag springTag = new Tag(2L, "스프링");
        final Tag aopTag = new Tag(3L, "AOP");
        final Tags tags = new Tags(List.of(javaTag, springTag, aopTag));

        final ArgumentCaptor<MemberTags> argumentCaptor = ArgumentCaptor.forClass(MemberTags.class);

        //when
        memberTagService.removeMemberTag(tags, member);

        //then
        verify(memberTagRepository).unregister(argumentCaptor.capture());
        final MemberTags memberTags = argumentCaptor.getValue();
        assertThat(memberTags.getValues()).extracting(MemberTag::getMemberId, MemberTag::getTagId)
            .containsExactlyInAnyOrder(
                tuple(member.getId(), javaTag.getId()),
                tuple(member.getId(), springTag.getId()),
                tuple(member.getId(), aopTag.getId())
            );
    }
}
