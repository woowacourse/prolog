package wooteco.prolog.studylog.domain;

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
@Document(indexName = "studylog-document")
public class StudylogDocument {

    @Id
    private Long id;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String content;

    public StudylogDocument(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
