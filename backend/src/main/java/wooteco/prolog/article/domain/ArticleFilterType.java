package wooteco.prolog.article.domain;

import lombok.Getter;

@Getter
public enum ArticleFilterType {
    ALL(""),
    ANDROID("안드로이드"),
    BACKEND("백엔드"),
    FRONTEND("프론트엔드");

    private final String partName;

    ArticleFilterType(String partName) {
        this.partName = partName;
    }
}
