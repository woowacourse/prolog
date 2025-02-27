package wooteco.prolog.session.infrastructure;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import wooteco.prolog.session.domain.QnaFeedbackContents;
import wooteco.prolog.session.domain.QnaFeedbackProvider;
import wooteco.prolog.session.domain.QnaFeedbackRequest;

@Profile({"local", "test"})
@Component
public final class FakeFeedbackProvider implements QnaFeedbackProvider {

    @Override
    public QnaFeedbackContents evaluate(final QnaFeedbackRequest request) {
        return new QnaFeedbackContents(
            "강점입니다.",
            "개선점입니다.",
            "추가학습 방법입니다.",
            1
        );
    }
}
