package wooteco.prolog.member.domain;

import static java.util.stream.Collectors.toList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.member.exception.MemberScrapNotExistException;
import wooteco.prolog.studylog.domain.Studylog;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberScrapStudylogs {

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MemberScrapStudylog> values = new HashSet<>();

    public MemberScrapStudylogs(List<MemberScrapStudylog> values) {
        this.values = new HashSet<>(values);
    }

    public void add(MemberScrapStudylog memberScrapStudylog) {
        this.values.add(memberScrapStudylog);
    }

    public void remove(Member member, Long studylogId) {
        this.values.remove(findByStudylogId(member, studylogId));
    }

    public List<Long> getScrapedStudylogIds(){
        return values.stream()
            .map(MemberScrapStudylog::getScrapStudylog)
            .map(Studylog::getId)
            .collect(toList());
    }

    private MemberScrapStudylog findByStudylogId(Member member, Long studylogId){
        return values.stream()
            .filter(memberScrapStudylog -> memberScrapStudylog.getMember().equals(member))
            .filter(memberScrapStudylog -> memberScrapStudylog.getScrapStudylog().getId().equals(studylogId))
            .findFirst()
            .orElseThrow(MemberScrapNotExistException::new);
    }
}
