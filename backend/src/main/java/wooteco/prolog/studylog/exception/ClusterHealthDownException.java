package wooteco.prolog.studylog.exception;

import wooteco.prolog.common.exception.ElasticsearchCustomException;

public class ClusterHealthDownException extends ElasticsearchCustomException {

    public ClusterHealthDownException(String status) {
        super(String.format("cluster의 상태가 %s 입니다.", status));
    }
}
