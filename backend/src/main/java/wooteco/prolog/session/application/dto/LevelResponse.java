package wooteco.prolog.session.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.session.domain.Level;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LevelResponse {

    private Long id;
    private String name;

    public static LevelResponse of(Level level) {
        return new LevelResponse(level.getId(), level.getName());
    }
}
