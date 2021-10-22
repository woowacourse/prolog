package wooteco.prolog.studylog.application.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.studylog.infrastructure.dto.ClusterHealth;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClusterHealthResponses {
    private List<ClusterHealth> clusters;

    public static ClusterHealthResponses from(List<ClusterHealth> clusterHealths) {
        return new ClusterHealthResponses(clusterHealths);
    }
}
