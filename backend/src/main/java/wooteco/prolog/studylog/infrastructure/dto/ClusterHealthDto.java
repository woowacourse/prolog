package wooteco.prolog.studylog.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ClusterHealthDto {

    @JsonIgnore
    private String epoch;
    private String timestamp;
    private String cluster;
    private String status;
    @JsonProperty(value = "node.total")
    private String nodeTotal;
    @JsonProperty(value = "node.data")
    private String nodeData;
    private String shards;
    private String pri;
    private String relo;
    private String init;
    private String unassign;
    @JsonProperty(value = "pending_tasks")
    private String pendingTasks;
    @JsonProperty(value = "max_task_wait_time")
    private String maxTaskWaitTime;
    @JsonProperty(value = "active_shards_percent")
    private String activeShardsPercent;
}
