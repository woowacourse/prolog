package wooteco.prolog.post.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wooteco.prolog.post.domain.Direction;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PageRequest {
    private Direction direction = Direction.DESC;
    private int page = 1;
    private int size = 20;

    public int calculateTotalPage(int totalSize) {
        if (totalSize % size == 0) {
            return totalSize / size;
        }
        return totalSize / size + 1;
    }
}
