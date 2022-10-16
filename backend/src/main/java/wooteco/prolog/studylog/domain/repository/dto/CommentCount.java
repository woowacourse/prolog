package wooteco.prolog.studylog.domain.repository.dto;

import wooteco.prolog.studylog.domain.Studylog;

public class CommentCount {

    private final Studylog studylog;

    private final long count;

    public CommentCount(final Studylog studylog, final long count) {
        this.studylog = studylog;
        this.count = count;
    }

    public Long getStudylogId() {
        return studylog.getId();
    }

    public long getCount() {
        return count;
    }
}
