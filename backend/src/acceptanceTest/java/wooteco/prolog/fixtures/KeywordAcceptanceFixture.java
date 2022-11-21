package wooteco.prolog.fixtures;

import wooteco.prolog.roadmap.application.dto.KeywordCreateRequest;
import wooteco.prolog.roadmap.application.dto.KeywordUpdateRequest;
import wooteco.prolog.roadmap.application.dto.QuizRequest;

public enum KeywordAcceptanceFixture {

    KEYWORD_REQUEST("키워드에 대한 설명입니다.");

    private final String description;

    KeywordAcceptanceFixture(final String description) {
        this.description = description;
    }

    public KeywordCreateRequest getSaveParent(final String keywordName, final int seq,
                                              final int importance) {
        return new KeywordCreateRequest(keywordName, this.description, seq, importance, null);
    }

    public KeywordCreateRequest getSaveChild(final String keywordName, final int seq,
                                             final int importance, final Long parentKeywordId) {
        return new KeywordCreateRequest(keywordName, this.description, seq, importance,
            parentKeywordId);
    }

    public KeywordUpdateRequest getUpdateParent(final String keywordName, final int seq,
                                                final int importance) {
        return new KeywordUpdateRequest(keywordName, this.description, seq, importance, null);
    }

    public QuizRequest getQuizRequest(final String quizContent) {
        return new QuizRequest(quizContent);
    }


}
