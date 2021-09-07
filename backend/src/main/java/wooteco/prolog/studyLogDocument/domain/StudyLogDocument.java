package wooteco.prolog.studyLogDocument.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@NoArgsConstructor
@Document(indexName = "study-log-document")
public class StudyLogDocument {

    @Id
    private Long id;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String content;

    public StudyLogDocument(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
