package wooteco.prolog.studylog.studylogtag.application;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.login.application.dto.GithubProfileResponse;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.session.application.LevelService;
import wooteco.prolog.session.application.MissionService;
import wooteco.prolog.studylog.application.StudylogService;
import wooteco.prolog.studylog.application.StudylogTagService;
import wooteco.prolog.session.application.dto.LevelRequest;
import wooteco.prolog.session.application.dto.LevelResponse;
import wooteco.prolog.session.application.dto.MissionRequest;
import wooteco.prolog.session.application.dto.MissionResponse;
import wooteco.prolog.studylog.application.dto.StudylogRequest;
import wooteco.prolog.studylog.application.dto.StudylogResponse;
import wooteco.prolog.studylog.application.dto.TagRequest;
import wooteco.prolog.studylog.application.dto.TagResponse;
import wooteco.prolog.studylog.domain.StudylogTag;
import wooteco.prolog.studylog.domain.Tag;
import wooteco.support.utils.IntegrationTest;

@IntegrationTest
class StudylogTagServiceTest {

    @Autowired
    private StudylogTagService studylogTagService;
    @Autowired
    private StudylogService studylogService;
    @Autowired
    private LevelService levelService;
    @Autowired
    private MissionService missionService;
    @Autowired
    private MemberService memberService;

    private Member member;

    @BeforeEach
    void setUp() {
        LevelResponse level = levelService.create(new LevelRequest("레벨1"));
        MissionResponse mission = missionService.create(new MissionRequest("미션 이름", level.getId()));

        this.member = memberService
            .findOrCreateMember(new GithubProfileResponse("이름", "별명", "1", "image"));
    }

    @DisplayName("스터디로그 태그가 등록되면 스터디로그 태그를 찾아올 수 있는지 확인한다.")
    @Test
    public void findAllTest() {
        //given
        List<TagRequest> tagRequests1 = createTagRequests("태그1", "태그2", "태그3", "태그5");
        List<TagRequest> tagRequests2 = createTagRequests("태그2", "태그3", "태그6");

        addTagRequestToStudylog(tagRequests1, tagRequests2);

        //when
        List<TagResponse> tags = studylogTagService.findTagsIncludedInStudylogs();

        //then
        List<String> tagNames = Arrays.asList("태그1", "태그2", "태그3", "태그5", "태그6");

        List<String> expectedTagNames = tags.stream()
            .map(TagResponse::getName)
            .collect(toList());

        assertThat(expectedTagNames).containsExactlyElementsOf(tagNames);
    }

    @SafeVarargs
    private final List<StudylogResponse> addTagRequestToStudylog(List<TagRequest>... tagRequests) {
        List<StudylogRequest> studylogs = Arrays.stream(tagRequests)
            .map(it -> new StudylogRequest("이름", "별명", 1L, it))
            .collect(toList());

        return studylogService.insertStudylogs(member.getId(), studylogs);
    }

    private List<TagRequest> createTagRequests(String... tags) {
        return Arrays.stream(tags)
            .map(TagRequest::new)
            .collect(toList());
    }

    @DisplayName("태그를 기반으로 스터디로그 태그를 조회할 수 있는지 확인")
    @Test
    @Transactional
    public void findByTags() {
        //given
        List<TagRequest> tagRequests1 = createTagRequests("태그1");
        List<TagRequest> tagRequests2 = createTagRequests("태그1", "태그2");
        List<TagRequest> tagRequests3 = createTagRequests("태그1", "태그2", "태그3");
        List<StudylogResponse> studylogResponses =
            addTagRequestToStudylog(tagRequests1, tagRequests2, tagRequests3);

        //when
        List<Tag> insertedTags = studylogResponses.stream()
            .flatMap(it -> it.getTags().stream())
            .map(tagResponse -> new Tag(tagResponse.getId(), tagResponse.getName()))
            .collect(toList());

        List<StudylogTag> studylogTags = studylogTagService.findByTags(insertedTags);

        assertThat(countTag(studylogTags, "태그1")).isEqualTo(3L);
        assertThat(countTag(studylogTags, "태그2")).isEqualTo(2L);
        assertThat(countTag(studylogTags, "태그3")).isEqualTo(1L);
    }

    private long countTag(List<StudylogTag> studylogTags, String tag) {
        return studylogTags.stream()
            .map(StudylogTag::getTag)
            .map(Tag::getName)
            .filter(name -> name.equals(tag))
            .count();
    }
}
