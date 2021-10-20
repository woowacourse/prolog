package wooteco.prolog.studylog.infrastructure.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IndexHealthDtos {
    private List<IndexHealthDto> indices;

    public static IndexHealthDtos from(List<IndexHealthDto> indexHealthDtos) {
        return new IndexHealthDtos(indexHealthDtos);
    }
}
