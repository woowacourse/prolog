package wooteco.prolog.fixtures;

import wooteco.prolog.roadmap.application.dto.KeywordCreateRequest;
import wooteco.prolog.roadmap.application.dto.KeywordUpdateRequest;

public enum KeywordAcceptanceFixture {

    KEYWORD_OF_ROOT("키워드에 대한 설명입니다.", null)
    ;

    private final String description;
    private final Long parentKeywordId;

    KeywordAcceptanceFixture(final String description, final Long parentKeywordId) {
        this.description = description;
        this.parentKeywordId = parentKeywordId;
    }

    public KeywordCreateRequest getSaveRequest(final String keywordName, final int seq, final int importance) {
        return new KeywordCreateRequest(keywordName, this.description, seq, importance, parentKeywordId);
    }

    public KeywordUpdateRequest getUpdateRequest(final String keywordName, final int seq, final int importance) {
        return new KeywordUpdateRequest(keywordName, this.description, seq, importance, parentKeywordId);
    }
}
