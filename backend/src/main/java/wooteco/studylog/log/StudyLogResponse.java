package wooteco.studylog.log;

public class StudyLogResponse {
    private Long id;
    private String title;
    private String contents;
    private String memberName;

    public StudyLogResponse(Long id, String title, String contents, String memberName) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.memberName = memberName;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public String getMemberName() {
        return memberName;
    }
}
