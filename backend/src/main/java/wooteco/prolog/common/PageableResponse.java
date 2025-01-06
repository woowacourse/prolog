package wooteco.prolog.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@AllArgsConstructor
@Getter
public class PageableResponse<T> {

    private static final int ONE_INDEXED_PARAMETER = 1;

    private List<T> data;
    private Long totalSize;
    private int totalPage;
    private int currentPage;

    public static <T> PageableResponse<T> of(List<T> data, Page<?> page) {
        return new PageableResponse<>(data, page.getTotalElements(), page.getTotalPages(),
            page.getNumber() + ONE_INDEXED_PARAMETER);
    }
}
