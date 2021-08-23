package wooteco.prolog.tag.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import wooteco.prolog.tag.dto.TagRequest;
import wooteco.prolog.tag.dto.TagResponse;
import wooteco.prolog.tag.exception.DuplicateTagException;

@ActiveProfiles("test")
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TagServiceTest {

    public static final TagRequest firstRequest = new TagRequest("소롱의글쓰기");
    public static final TagRequest secondRequest = new TagRequest("스프링");
    public static final TagRequest thirdRequest = new TagRequest("감자튀기기");
    public static final TagRequest fourthRequest = new TagRequest("집필왕웨지");
    public static final TagRequest fifthRequest = new TagRequest("PK대신현구막");
    public static final List<TagRequest> tagRequests = new ArrayList<>(Arrays.asList(
        firstRequest, secondRequest, thirdRequest, fourthRequest, fifthRequest
    ));

    @Autowired
    private TagService tagService;

    @DisplayName("태그 생성 메서드 테스트")
    @Test
    void createTest() {
        //given
        //when
        tagService.findOrCreate(tagRequests);
        List<TagResponse> expectedResults = tagService.findAll();

        //then
        List<String> givenNames = getNamesFromTagRequest(tagRequests);
        List<String> expectedNames = getNamesFromTagResponse(expectedResults);

        assertThat(givenNames).containsExactlyElementsOf(expectedNames);
    }


    @DisplayName("기존에 저장되어있는 태그가 있을 때, 신규건만 저장되는지 확인")
    @Test
    void onlyNewTagSaveTest() {
        //given
        tagService.findOrCreate(tagRequests);
        List<TagRequest> newTagRequests = Arrays.asList(
            new TagRequest("새로운태그1"),
            new TagRequest("새로운태그2"),
            new TagRequest("새로운태그3")
        );

        //when
        List<TagRequest> addedTagRequest = tagRequests;
        addedTagRequest.addAll(newTagRequests); // 신규 건수 3건 추가, 총 8건
        tagService.findOrCreate(addedTagRequest);

        List<TagResponse> expectedResults = tagService.findAll();

        //then
        List<String> givenNames = getNamesFromTagRequest(addedTagRequest);
        List<String> expectedNames = getNamesFromTagResponse(expectedResults);

        assertThat(expectedResults.size())
            .isEqualTo(addedTagRequest.size()); // 중복된 것은 insert되지 않고 8개만 입력되는지 확인
        assertThat(givenNames).containsExactlyElementsOf(expectedNames);
    }

    @DisplayName("중복되는 태그 요청인 경우 예외 발생 확인")
    @Test
    void duplicateTest() {
        //given
        ArrayList<TagRequest> tagRequests = new ArrayList<>(TagServiceTest.tagRequests);
        tagRequests.add(firstRequest);
        //when
        //then
        assertThatThrownBy(() -> tagService.findOrCreate(tagRequests))
            .isExactlyInstanceOf(DuplicateTagException.class);
    }

    private List<String> getNamesFromTagRequest(List<TagRequest> tags) {
        return tags.stream()
            .map(TagRequest::getName)
            .collect(Collectors.toList());
    }

    private List<String> getNamesFromTagResponse(List<TagResponse> expectedResults) {
        return expectedResults.stream()
            .map(TagResponse::getName)
            .collect(Collectors.toList());
    }
}
