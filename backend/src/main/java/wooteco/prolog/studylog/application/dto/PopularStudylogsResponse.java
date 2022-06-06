package wooteco.prolog.studylog.application.dto;

public class PopularStudylogsResponse {

    private StudylogsResponse allResponse;
    private StudylogsResponse frontResponse;
    private StudylogsResponse backResponse;

    public PopularStudylogsResponse(StudylogsResponse allResponse,
                                    StudylogsResponse frontResponse,
                                    StudylogsResponse backResponse) {
        this.allResponse = allResponse;
        this.frontResponse = frontResponse;
        this.backResponse = backResponse;
    }

    public StudylogsResponse getAllResponse() {
        return allResponse;
    }

    public StudylogsResponse getFrontResponse() {
        return frontResponse;
    }

    public StudylogsResponse getBackResponse() {
        return backResponse;
    }

    @Override
    public String toString() {
        return "PopularStudylogsResponse{" +
            "allResponse=" + allResponse +
            ", frontResponse=" + frontResponse +
            ", backResponse=" + backResponse +
            '}';
    }
}
