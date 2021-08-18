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
        tagService.create(tagRequests);
        List<TagResponse> expectedResults = tagService.findAllWithPost();

        //then
        List<String> givenNames = tagRequests.stream()
                .map(TagRequest::getName)
                .collect(Collectors.toList());

        List<String> expectedNames = expectedResults.stream()
                .map(TagResponse::getName)
                .collect(Collectors.toList());

        assertThat(givenNames).containsExactlyElementsOf(expectedNames);
    }

    @DisplayName("기존에 저장되어있는 태그가 있을 때, 신규건만 저장되는지 확인")
    @Test
    void onlyNewTagSaveTest() {
        //given
        tagService.create(tagRequests); // 5개 입력
        //when
        List<TagRequest> newTagRequests = Arrays.asList(
                new TagRequest("새로운태그1"),
                new TagRequest("새로운태그2"),
                new TagRequest("새로운태그3")
        );

        ArrayList<TagRequest> addedTagRequest = new ArrayList<>(tagRequests);
        addedTagRequest.addAll(newTagRequests); // 신규 건수 3건 추가, 총 8건
        tagService.create(addedTagRequest);

        List<TagResponse> expectedResults = tagService.findAll();

        //then
        List<String> givenNames = addedTagRequest.stream()
                .map(TagRequest::getName)
                .collect(Collectors.toList());

        List<String> expectedNames = expectedResults.stream()
                .map(TagResponse::getName)
                .collect(Collectors.toList());

        for (TagResponse expectedResult : expectedResults) {
            assertThat(expectedResult.getId()).isEqualTo(addedTagRequest.size()); // 중복된 것은 insert되지 않고 8개만 입력되는지 확인
        }
        assertThat(expectedResults.size()).isEqualTo(addedTagRequest.size()); // 중복된 것은 insert되지 않고 8개만 입력되는지 확인
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
        assertThatThrownBy(() -> tagService.create(tagRequests))
                .isExactlyInstanceOf(DuplicateTagException.class);
    }
}
