package wooteco.prolog.studylog.exception;

import wooteco.prolog.common.exception.ElasticsearchCustomException;

public class ElasticsearchConnectException extends ElasticsearchCustomException {

    public ElasticsearchConnectException(String uri) {
        super(String.format("Elasticsearch로의 연결이 끊어져 %s로 요청할 수 없습니다.", uri));
    }
}
