package wooteco.prolog.report.application.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PageableResponse<T> {

    private List<T> data;
    private Long totalSize;
    private int totalPage;
    private int currentPage;

}
