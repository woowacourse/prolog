package wooteco.prolog.report.application.report;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import wooteco.prolog.report.domain.report.Report;

public interface ReportsRequestType {
    boolean isSupport(String type);
    Object execute(String username, Pageable pageable);

    default Page<Report> sort(Page<Report> reports) {
        List<Report> sorted = reports.stream()
            .sorted(comparing(Report::isRepresent)
                .thenComparing(Report::getCreatedAt)
                .reversed()
            ).collect(toList());

        return new PageImpl<>(sorted, reports.getPageable(), reports.getTotalElements());
    }
}
