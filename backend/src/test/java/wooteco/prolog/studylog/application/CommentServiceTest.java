package wooteco.prolog.studylog.application;

import static org.assertj.core.api.Assertions.*;
import org.junit.Test;
import wooteco.prolog.studylog.application.dto.CommentSaveRequest;


public class CommentServiceTest {
    private CommentService commentService;
    @Test
    public void 댓글_추가(){
        assertThatThrownBy(
            ()->commentService.insertComment(new CommentSaveRequest(1L,1L,"테스트 문장입니다."))
        ).doesNotThrowAnyException();
    }

}
