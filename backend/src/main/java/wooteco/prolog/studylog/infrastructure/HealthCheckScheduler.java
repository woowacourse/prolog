package wooteco.prolog.studylog.infrastructure;

import java.util.List;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import wooteco.prolog.studylog.exception.ClusterHealthDownException;
import wooteco.prolog.studylog.exception.IndexHealthDownException;
import wooteco.prolog.studylog.infrastructure.dto.ClusterHealth;
import wooteco.prolog.studylog.infrastructure.dto.IndexHealth;

@Component
public class HealthCheckScheduler {

    private final HealthCheckClient healthCheckClient;

    public HealthCheckScheduler(HealthCheckClient healthCheckClient) {
        this.healthCheckClient = healthCheckClient;
    }

    @Scheduled(fixedDelay = 10000)
    public void clusterHealthCheckTask() {
        List<ClusterHealth> clusters = healthCheckClient.healthOfCluster().getClusters();
        for (ClusterHealth cluster : clusters) {
            if (!cluster.isGreen()) {
                throw new ClusterHealthDownException(cluster.getStatus());
            }
        }
    }

    @Scheduled(fixedDelay = 10000)
    public void indexHealthCheckTask() {
        List<IndexHealth> indices = healthCheckClient.healthOfIndex("studylog-document").getIndices();
        for (IndexHealth index : indices) {
            if (!index.isGreen()) {
                throw new IndexHealthDownException(index.getHealth());
            }
        }
    }
}
