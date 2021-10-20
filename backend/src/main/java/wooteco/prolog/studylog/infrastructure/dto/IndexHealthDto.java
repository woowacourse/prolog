package wooteco.prolog.studylog.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class IndexHealthDto {

    private String health;
    private String status;
    private String index;
    @JsonIgnore
    private String uuid;
    @JsonIgnore
    private String pri;
    @JsonIgnore
    private String rep;
    @JsonProperty(value = "docs.count")
    private String docsCount;
    @JsonProperty(value = "docs.deleted")
    private String docsDeleted;
    @JsonProperty(value = "store.size")
    private String storeSize;
    @JsonProperty(value = "pri.store.size")
    private String priStoreSize;
}
