package wooteco.prolog.session.application.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class SessionMemberRequest {

    private List<Long> memberIds;
}
