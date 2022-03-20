package wooteco.prolog.studylog.domain;

import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.member.domain.Role;


/**
 * wooteco.prolog.member.application.dto.MemberResponse 클래스에 기반을 둔 도메인
 * @author Hyeon9mak
 */
@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class MostPopularStudylogAuthor {


    private Long id;
    private String username;
    private String nickname;
    private Role role;
    private String imageUrl;
}
