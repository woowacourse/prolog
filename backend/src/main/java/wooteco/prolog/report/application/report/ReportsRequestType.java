package wooteco.prolog.report.application.report;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import wooteco.prolog.report.domain.report.Report2;

public interface ReportsRequestType {
    boolean isSupport(String type);
    Object execute(String username, Pageable pageable);

    default Page<Report2> sort(Page<Report2> reports) {
        List<Report2> sorted = reports.stream()
            .sorted(comparing(Report2::isRepresent)
                .thenComparing(Report2::getCreatedAt)
                .reversed()
            ).collect(toList());

        return new PageImpl<>(sorted, reports.getPageable(), reports.getTotalElements());
    }
}
