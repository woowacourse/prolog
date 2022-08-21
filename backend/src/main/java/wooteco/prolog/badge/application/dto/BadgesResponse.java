package wooteco.prolog.badge.application.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BadgesResponse {

    private List<BadgeResponse> badges;
}
