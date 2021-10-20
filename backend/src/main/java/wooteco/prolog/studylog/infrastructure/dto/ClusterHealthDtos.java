package wooteco.prolog.studylog.infrastructure.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClusterHealthDtos {
    private List<ClusterHealthDto> clusters;

    public static ClusterHealthDtos from(List<ClusterHealthDto> clusterHealthDtos) {
        return new ClusterHealthDtos(clusterHealthDtos);
    }
}
