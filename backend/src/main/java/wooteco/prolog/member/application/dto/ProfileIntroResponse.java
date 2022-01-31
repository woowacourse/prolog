package wooteco.prolog.member.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.member.domain.Member;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ProfileIntroResponse {

    private String text;

    public static ProfileIntroResponse of(Member member) {
        return new ProfileIntroResponse(member.getProfileIntro());
    }
}
