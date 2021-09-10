package wooteco.prolog.common.fixture.report;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.studylog.domain.Level;
import wooteco.prolog.studylog.domain.Mission;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.Tag;
import wooteco.prolog.studylog.domain.ablity.Ability;
import wooteco.prolog.studylog.domain.report.Report;
import wooteco.prolog.studylog.domain.report.ReportedAbility;

public class ReportFixture {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String title;
        private String description;
        private List<ReportedAbility> reportedAbilities = new ArrayList<>();
        private Member member;
        private Studylog  studylog;

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

        public Builder reportedAbilities(ReportedAbility reportedAbilities) {
            this.reportedAbilities.add(reportedAbilities);
            return this;
        }

        public Builder member(Member member) {
            this.member = member;
            return this;
        }

        public Builder studylog(Studylog studylog) {
            this.studylog = studylog;
            return this;
        }

        public Report build() {
            return new Report(
                this.id,
                this.title,
                this.description,
                this.reportedAbilities,
                this.member,
                this.studylog
            );
        }
    }
    public static Report get() {
        return new Report(
            "testReport",
            "description",
            Arrays.asList(
                new ReportedAbility(
                    createStudylog(),
                    createAbility()
                )
            ),
            createMember(),
            createStudylog()
        );
    }

    private static Studylog createStudylog() {
        return new Studylog(
            createMember(),
            "studylog title",
            "studylog content",
            createMission(),
            Arrays.asList(
                new Tag("test tag1"),
                new Tag("test tag1")
            )
        );
    }

    private static Member createMember() {
        return new Member("bperhaps", "손너잘", Role.CREW, 2222L,
            "https://avatars.githubusercontent.com/u/51393021?v=4");
    }

    private static Mission createMission() {
        return new Mission("test mission", new Level("test Level"));
    }

    private static Ability createAbility() {
        return Ability.parent("test ability", "test ability description", "test color");
    }

}
