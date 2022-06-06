package wooteco.prolog.common.fixture.studylog;

import java.util.ArrayList;
import java.util.List;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.session.domain.Mission;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.Tag;

public class StudylogFixture {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Member member;
        private String title;
        private String content;
        private Mission mission;
        private List<Tag> tags = new ArrayList<>();

        public Builder member(Member member) {
            this.member = member;
            return this;
        }


        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder mission(Mission mission) {
            this.mission = mission;
            return this;
        }

        public Studylog build() {
            return new Studylog(
                this.member,
                this.title,
                this.content,
                this.mission,
                this.tags
            );
        }
    }

}
