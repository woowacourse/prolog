package wooteco.prolog.studylog.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.session.domain.Mission;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.studylog.application.dto.TagRequest;
import wooteco.prolog.studylog.application.dto.TagResponse;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.StudylogTag;
import wooteco.prolog.studylog.domain.Tag;
import wooteco.prolog.studylog.domain.Tags;
import wooteco.prolog.studylog.domain.repository.TagRepository;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    private static final TagRequest JAVA_TAG_REQUEST = new TagRequest("자바");
    private static final TagRequest COLLECTION_TAG_REQUEST = new TagRequest("컬렉션");
    private static final Session TEST_SESSION = new Session(4L, 5L, "세션");
    private static final Mission TEST_MISSION = new Mission(6L, "레벨 2 - 웹 자동차 경주", TEST_SESSION);
    private static final Member TEST_MEMBER_CREW1 = new Member(1L, "홍혁준", "홍실", Role.CREW, 2L, null, null);
    private static final Member TEST_MEMBER_CREW2 = new Member(2L, "송세연", "아마란스", Role.CREW, 2L, null, null);
    private static final Studylog TEST_STUDYLOG1 = new Studylog(TEST_MEMBER_CREW1, "레벨 1 레벨인터뷰", "레벨인터뷰에 대한 내용입니다.",
        TEST_MISSION, Collections.emptyList());
    private static final Studylog TEST_STUDYLOG2 = new Studylog(TEST_MEMBER_CREW2, "레벨 1 레벨인터뷰", "레벨인터뷰에 대한 내용입니다.",
        TEST_MISSION, Collections.emptyList());

    @Mock
    private StudylogTagService studylogTagService;
    @Mock
    private TagRepository tagRepository;
    @InjectMocks
    private TagService tagService;

    @DisplayName("Tags를 찾고, 없으면 생성하는 기능 테스트")
    @Test
    void findOrCreateTest() {
        //given
        List<TagRequest> tagRequests = Arrays.asList(JAVA_TAG_REQUEST, COLLECTION_TAG_REQUEST);
        when(tagRepository.findByNameValueIn(anyList())).then(this::getFirstElementTags);

        //when
        Tags tags = tagService.findOrCreate(tagRequests);

        //then
        assertAll(() -> verify(tagRepository).saveAll(tags.getList().subList(1, 2)),
            () -> assertThat(tags.getList()).extracting(Tag::getName)
                .contains(JAVA_TAG_REQUEST.getName(), COLLECTION_TAG_REQUEST.getName()));
    }

    private List<Tag> getFirstElementTags(InvocationOnMock invocation) {
        List<Tag> existTags = new ArrayList<>();
        List<String> argument = invocation.getArgument(0);
        Tag existTag = new Tag(argument.get(0));
        existTags.add(existTag);
        return existTags;
    }

    @DisplayName("스터디 로그에 쓰인 모든 태그들을 찾는 기능 테스트")
    @Test
    void findTagsIncludedStudylogsTest() {
        //given
        StudylogTag studylog1 = new StudylogTag(TEST_STUDYLOG1, new Tag(1L, "스프링"));
        StudylogTag studylog2 = new StudylogTag(TEST_STUDYLOG1, new Tag(2L, "자바"));
        doReturn(Arrays.asList(studylog1, studylog2)).when(studylogTagService).findAll();

        //when
        List<TagResponse> foundTags = tagService.findTagsIncludedInStudylogs();

        //then
        assertThat(foundTags).extracting(TagResponse::getName).contains("자바", "스프링");
    }
}
