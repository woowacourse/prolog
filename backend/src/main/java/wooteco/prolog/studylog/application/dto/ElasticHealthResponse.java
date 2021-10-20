package wooteco.prolog.studylog.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ElasticHealthResponse {

    private ClusterHealthResponses clusterHealthResponsesInfo;
    private IndexHealthResponses indexHealthInfo;

    public static ElasticHealthResponse of(ClusterHealthResponses clusterHealthResponses,
                                           IndexHealthResponses indexHealthResponses) {
        return new ElasticHealthResponse(clusterHealthResponses, indexHealthResponses);
    }
}
