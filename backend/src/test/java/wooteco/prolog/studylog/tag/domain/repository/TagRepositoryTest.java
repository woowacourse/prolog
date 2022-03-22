package wooteco.prolog.studylog.tag.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import wooteco.prolog.studylog.domain.Tag;
import wooteco.prolog.studylog.domain.repository.TagRepository;
import wooteco.support.utils.RepositoryTest;

@RepositoryTest
public class TagRepositoryTest {

    @Autowired
    private TagRepository tagRepository;

    @DisplayName("Tag 생성")
    @Test
    void createTag() {
        // given
        Tag tag = new Tag("나는 말하는 감자");

        // when
        Tag savedTag = tagRepository.save(tag);

        // then
        assertThat(savedTag.getId()).isNotNull();
        assertThat(savedTag).usingRecursiveComparison()
            .ignoringFields("id", "createdAt", "updatedAt")
            .isEqualTo(tag);
    }

    @DisplayName("name 리스트로 Tag 리스트 검색 - 성공")
    @Test
    void findByNameValueIn() {
        // given
        String name1 = "나는 말하는 감자";
        String name2 = "나는 춤추는 고구마";
        String name3 = "나는 꿈꾸는 옥수수";

        Tag tag1 = 태그를_생성한다(name1);
        Tag tag2 = 태그를_생성한다(name2);
        Tag tag3 = 태그를_생성한다(name3);

        List<String> names = Arrays.asList(name1, name2, name3);

        // when
        List<Tag> tags = tagRepository.findByNameValueIn(names);

        // then
        assertThat(tags).usingFieldByFieldElementComparator()
            .containsExactlyInAnyOrder(tag1, tag2, tag3);
    }

    @DisplayName("name 리스트로 Tag 리스트 검색 - 검색 결과 없음")
    @Test
    void findByNameValueInFailed() {
        // given
        String name1 = "나는 말하는 감자";
        String name2 = "나는 춤추는 고구마";
        String name3 = "나는 꿈꾸는 옥수수";

        List<String> names = Arrays.asList(name1, name2, name3);

        // when
        List<Tag> tags = tagRepository.findByNameValueIn(names);

        // then
        assertThat(tags).isEmpty();
    }

    private Tag 태그를_생성한다(String name) {
        return tagRepository.save(new Tag(name));
    }
}
