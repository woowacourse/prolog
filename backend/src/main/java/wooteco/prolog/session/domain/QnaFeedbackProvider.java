package wooteco.prolog.session.domain;

public interface QnaFeedbackProvider {

    QnaFeedbackContents evaluate(QnaFeedbackRequest request);
}
