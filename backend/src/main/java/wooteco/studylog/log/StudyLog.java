package wooteco.studylog.log;

import java.util.List;

public class StudyLog {
    private Long logId;
    private Long categoryId;
    private String title;
    private List<String> tags;
    private String content;

    public StudyLog(Long logId, Long categoryId, String title, List<String> tags, String content) {
        this.logId = logId;
        this.categoryId = categoryId;
        this.title = title;
        this.tags = tags;
        this.content = content;
    }

    public StudyLog(Long categoryId, String title, List<String> tags, String content) {
        this(null, categoryId, title, tags, content);
    }
}
