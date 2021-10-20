package wooteco.prolog.studylog.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.studylog.infrastructure.dto.ClusterHealthDtos;
import wooteco.prolog.studylog.infrastructure.dto.IndexHealthDtos;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ElasticHealthResponse {

    private ClusterHealthDtos clusterHealthInfo;
    private IndexHealthDtos indexHealthInfo;

    public static ElasticHealthResponse of(ClusterHealthDtos clusterHealth,
                                           IndexHealthDtos indexHealth) {
        return new ElasticHealthResponse(clusterHealth, indexHealth);
    }
}
