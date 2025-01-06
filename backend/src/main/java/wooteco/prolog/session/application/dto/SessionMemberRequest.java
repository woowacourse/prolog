package wooteco.prolog.session.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SessionMemberRequest {

    private List<Long> memberIds;
}
