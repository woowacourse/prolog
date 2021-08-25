package wooteco.prolog.studylog.tag.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.prolog.studylog.domain.Tag;
import wooteco.prolog.studylog.domain.Tags;
import wooteco.prolog.studylog.exception.DuplicateTagException;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TagsTest {

    private static final Tag 자바 = new Tag("자바");
    private static final Tag 옵셔널 = new Tag("옵셔널");
    private static final Tag 자바스크립트 = new Tag("자바스크립트");
    private static final Tag 현구막의인프라 = new Tag("현구막의인프라");
    private static final Tag 현구막의브론즈롤 = new Tag("현구막의브론즈롤");

    @DisplayName("Tags 생성테스트")
    @Test
    void createTest() {
        //given
        List<Tag> tags = Arrays.asList(자바, 옵셔널, 자바스크립트, 현구막의인프라);

        //then
        assertThat(new Tags(tags)).isNotNull();
    }

    @DisplayName("태그 이름이 중복될 때 예외 발생하는지 확인")
    @Test
    void tagsCreateTestWhenDuplicateTagName() {
        //given
        List<Tag> tags = Arrays.asList(현구막의브론즈롤, 현구막의브론즈롤);

        //then
        assertThatThrownBy(() -> new Tags(tags))
                .isExactlyInstanceOf(DuplicateTagException.class);
    }

    @DisplayName("Tags 간 이름비교를 통한 Tag 제거 테스트")
    @Test
    void removeAllTest() {
        //given
        Tags thisTags = new Tags(Arrays.asList(자바, 옵셔널, 자바스크립트));
        Tags thatTags = new Tags(Arrays.asList(옵셔널, 자바스크립트));

        //when
        Tags newTags = thisTags.removeAllByName(thatTags);

        //then
        assertThat(newTags.getList()).containsExactly(자바);
    }

    @DisplayName("Tags 간 이름비교를 통한 Tag 제거 테스트2")
    @Test
    void removeAllTest2() {
        //given
        Tags thisTags = new Tags(Arrays.asList(자바, 옵셔널, 자바스크립트));
        Tags thatTags = new Tags(Arrays.asList(자바, 옵셔널, 자바스크립트, 현구막의인프라));

        //when
        Tags newTags = thisTags.removeAllByName(thatTags);

        //then
        assertThat(newTags.getList()).isEmpty();
    }
}