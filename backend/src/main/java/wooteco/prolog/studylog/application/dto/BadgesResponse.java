package wooteco.prolog.studylog.application.dto;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import wooteco.prolog.studylog.domain.Badge;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BadgesResponse {

    private List<BadgeResponse> badges;
}
