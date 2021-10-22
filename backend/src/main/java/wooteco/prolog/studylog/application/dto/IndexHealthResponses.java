package wooteco.prolog.studylog.application.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.studylog.infrastructure.dto.IndexHealth;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IndexHealthResponses {
    private List<IndexHealth> indices;

    public static IndexHealthResponses from(List<IndexHealth> indexHealths) {
        return new IndexHealthResponses(indexHealths);
    }
}
