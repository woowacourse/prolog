package wooteco.prolog.ability.application.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudylogAbilityRequest {

    private List<Long> abilities;

}
