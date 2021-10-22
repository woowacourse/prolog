package wooteco.prolog.studylog.exception;

import wooteco.prolog.common.exception.ElasticsearchCustomException;

public class ElasticsearchInternalException extends ElasticsearchCustomException {

    public ElasticsearchInternalException() {
        super("Elasticsearch에서 알 수 없는 에러가 발생했습니다.");
    }
}
