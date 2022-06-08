package wooteco.prolog.report.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import wooteco.prolog.common.PageableResponse;
import wooteco.prolog.report.application.dto.ReportResponse;
import wooteco.prolog.report.domain.Report;
import wooteco.prolog.report.domain.repository.ReportRepository;
import wooteco.support.utils.IntegrationTest;

@IntegrationTest
class ReportServiceTest {

    @Autowired
    private ReportService reportService;

    @Autowired
    private ReportRepository reportRepository;

    @DisplayName("정렬된 리포트 목록 조회")
    @Test
    void findAllSortedReports() {
        Report report1 = new Report("title1", "desc1", 1L, LocalDate.of(2022, 3, 5),
            LocalDate.of(2022, 3, 6));
        Report report2 = new Report("title2", "desc2", 1L, LocalDate.of(2022, 3, 6),
            LocalDate.of(2022, 3, 7));
        Report report3 = new Report("title3", "desc3", 1L, LocalDate.of(2022, 4, 5),
            LocalDate.of(2022, 4, 10));

        reportRepository.save(report1);
        reportRepository.save(report2);
        reportRepository.save(report3);

        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.DESC, "startDate");
        PageableResponse<ReportResponse> allReports = reportService.findReports(pageable);
        List<Long> expectIds = allReports.getData().stream()
            .map(ReportResponse::getId)
            .collect(Collectors.toList());

        assertThat(expectIds).containsExactly(report3.getId(), report2.getId(), report1.getId());
    }
}
