package wooteco.prolog.session.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.member.domain.Member;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@EqualsAndHashCode(exclude = "id")
public class SessionMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long sessionId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public SessionMember(Long sessionId, Member member) {
        this.sessionId = sessionId;
        this.member = member;
    }
}
