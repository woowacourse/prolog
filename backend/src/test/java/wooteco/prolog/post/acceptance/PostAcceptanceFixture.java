package wooteco.prolog.post.acceptance;

import wooteco.prolog.post.application.dto.PostRequest;
import wooteco.prolog.tag.dto.TagRequest;

import java.util.Arrays;

public class PostAcceptanceFixture {
    public static PostRequest firstPost = new PostRequest(
            "[자바][옵셔널] 학습log 제출합니다.",
            "옵셔널은 NPE를 배제하기 위해 만들어진 자바8에 추가된 라이브러리입니다. \n " +
                    "다양한 메소드를 호출하여 원하는 대로 활용할 수 있습니다",
            1L,
            Arrays.asList(
                    new TagRequest("자바"),
                    new TagRequest("Optional")
            )
    );

    public static PostRequest secondPost = new PostRequest("[자바스크립트][비동기] 학습log 제출합니다.",
            "모던 JS의 fetch문, ajax라이브러리인 axios등을 통해 비동기 요청을 \n " +
                    "편하게 할 수 있습니다. 자바 최고",
            2L,
            Arrays.asList(
                    new TagRequest("자바스크립트"),
                    new TagRequest("비동기")
            )
    );
}
