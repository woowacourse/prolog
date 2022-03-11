package wooteco.prolog.common.fixture.report;

import wooteco.prolog.member.domain.Member;
import wooteco.prolog.report.domain.report.Report2;
import wooteco.prolog.report.domain.report.abilitygraph.AbilityGraph;
import wooteco.prolog.report.domain.report.studylog.ReportedStudylogs;

public class ReportFixture {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String title;
        private String description;
        private AbilityGraph abilityGraph;
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

        public Builder graph(AbilityGraph abilityGraph) {
            this.abilityGraph = abilityGraph;
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

        public Report2 build() {
            return new Report2(
                this.id,
                this.title,
                this.description,
                this.abilityGraph,
                this.studylogs,
                isRepresent,
                this.member
            );
        }
    }
}
