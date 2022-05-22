package wooteco.prolog.studylog.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.session.domain.Mission;
import wooteco.prolog.session.domain.Session;

import javax.persistence.*;
import java.util.List;

import static java.util.stream.Collectors.toList;

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
    private StudylogTags studylogTags;

    public StudylogTemp(Member member, String title, String content, Session session, Mission mission, List<Tag> tags) {
        this.member = member;
        this.title = title;
        this.content = content;
        this.session = session;
        this.mission = mission;
        Tags tags1 = new Tags(tags);
        this.studylogTags = new StudylogTags(tags1.getList().stream()
                .map(tag -> new StudylogTag(null, tag))
                .collect(toList()));
    }
}
