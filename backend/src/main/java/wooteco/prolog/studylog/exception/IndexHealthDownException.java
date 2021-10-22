package wooteco.prolog.studylog.exception;

import wooteco.prolog.common.exception.ElasticsearchCustomException;

public class IndexHealthDownException extends ElasticsearchCustomException {

    public IndexHealthDownException(String status) {
        super(String.format("index의 상태가 %s 입니다.", status));
    }
}
