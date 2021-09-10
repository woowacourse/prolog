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

    @Field(type = FieldType.Text)
    private String tagName;

    @Field(type = FieldType.Text)
    private String missionName;

    @Field(type = FieldType.Text)
    private String levelName;

    @Field(type = FieldType.Text)
    private String userName;

    public StudylogDocument(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public void setMissionName(String missionName) {
        this.missionName = missionName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
