package wooteco.prolog.studylog.application;


import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.studylog.application.dto.TagResponse;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.StudylogTag;
import wooteco.prolog.studylog.domain.Tag;
import wooteco.prolog.studylog.domain.repository.StudylogTagRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudylogTagServiceTest {

    private static final Tag TAG_JAVA = new Tag("자바");
    private static final Tag TAG_JCF = new Tag("JCF");
    private static final List<Tag> TAG_LIST = ImmutableList.of(TAG_JAVA, TAG_JCF);
    private static final Member MEMBER = new Member(1L, "홍혁준", "홍실", Role.CREW, 1234L, "imageUrl");
    private static final Session SESSION = new Session("2023 백엔드 레벨 1");
    private static final Studylog STUDYLOG = new Studylog(MEMBER, "페이지 이름", "페이지 내용", SESSION, null,
        TAG_LIST);

    @Mock
    private StudylogTagRepository studylogTagRepository;
    @InjectMocks
    private StudylogTagService studylogTagService;

    @DisplayName("StudylogTagRepository에 있는 모든 StudylogTag를 찾는다.")
    @Test
    void findAll() {
        //given
        when(studylogTagRepository.findAll())
            .thenReturn(ImmutableList.of(new StudylogTag(1L, STUDYLOG, TAG_JAVA),
                new StudylogTag(2L, STUDYLOG, TAG_JCF)));

        //when
        final List<StudylogTag> studylogTags = studylogTagService.findAll();

        //then
        assertThat(studylogTags)
            .extracting(StudylogTag::getId)
            .contains(1L, 2L);
    }

    @DisplayName("Studylog에 포함된 모든 Tag를 찾는다.")
    @Test
    void findTagsIncludedInStudylogs() {
        //given
        when(studylogTagRepository.findTagsIncludedInStudylogs())
            .thenReturn(TAG_LIST);

        //when
        final List<TagResponse> tagResponses =
            studylogTagService.findTagsIncludedInStudylogs();

        //then
        assertThat(tagResponses)
            .extracting(TagResponse::getName)
            .contains(TAG_JAVA.getName(), TAG_JCF.getName());
    }
}
