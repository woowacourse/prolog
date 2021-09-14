package wooteco.prolog.common.fixture.report;

import wooteco.prolog.member.domain.Member;
import wooteco.prolog.studylog.domain.report.Report;
import wooteco.prolog.studylog.domain.report.abilitygraph.Graph;
import wooteco.prolog.studylog.domain.report.studylog.ReportedStudylogs;

public class ReportFixture {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String title;
        private String description;
        private Graph graph;
        private ReportedStudylogs studylogs;
        private Boolean isRepresent;
        private Member member;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder graph(Graph graph) {
            this.graph = graph;
            return this;
        }

        public Builder member(Member member) {
            this.member = member;
            return this;
        }

        public Builder studylogs(ReportedStudylogs reportedStudylogs) {
            this.studylogs = reportedStudylogs;
            return this;
        }

        public Builder isPresent(boolean isPresent) {
            this.isRepresent = isPresent;
            return this;
        }

        public Report build() {
            return new Report(
                this.id,
                this.title,
                this.description,
                this.graph,
                this.studylogs,
                isRepresent,
                this.member
            );
        }
    }
}
