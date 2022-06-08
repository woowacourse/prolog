package wooteco.prolog.studylog.domain;

import static java.util.stream.Collectors.toList;

import java.util.List;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.session.domain.Mission;
import wooteco.prolog.session.domain.Session;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class StudylogTemp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id")
    private Session session;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;

    @Embedded
    private StudylogTempTags studylogTempTags;

    public StudylogTemp(Member member, String title, String content, Session session, Mission mission, List<Tag> tagList) {
        this.member = member;
        this.title = title;
        this.content = content;
        this.session = session;
        this.mission = mission;
        Tags tags = new Tags(tagList);
        this.studylogTempTags = new StudylogTempTags(tags.getList().stream()
            .map(tag -> new StudylogTempTag(this, tag))
            .collect(toList()));
    }
}
