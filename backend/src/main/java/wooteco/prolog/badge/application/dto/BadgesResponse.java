package wooteco.prolog.badge.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BadgesResponse {

    private List<BadgeResponse> badges;
}
