package wooteco.prolog.studylog.domain;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Document(indexName = "studylog-document")
public class StudylogDocument {

    @Field(type = FieldType.Long)
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
    private String username;

    @Field(type = FieldType.Date, format = DateFormat.basic_date)
    private LocalDateTime dateTime;
}
