package wooteco.prolog.studylog.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "search-document")
public class SearchDocument {

    @Id
    private Long id;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String content;

    @Field(type = FieldType.Auto)
    private List<Long> tagIds;

    @Field(type = FieldType.Long)
    private Long missionId;

    @Field(type = FieldType.Long)
    private Long levelId;

    @Field(type = FieldType.Text)
    private String userName;

}
