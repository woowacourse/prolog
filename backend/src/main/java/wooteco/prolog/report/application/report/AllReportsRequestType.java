package wooteco.prolog.report.application.report;

import static java.util.stream.Collectors.toList;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.testcontainers.shaded.org.apache.commons.lang.StringUtils;
import wooteco.prolog.report.application.dto.report.ReportAssembler;
import wooteco.prolog.report.application.dto.report.response.ReportPageableResponse;
import wooteco.prolog.report.application.dto.report.response.ReportResponse;
import wooteco.prolog.report.domain.report.Report;
import wooteco.prolog.report.domain.report.repository.ReportRepository;

@Component
public class AllReportsRequestType implements ReportsRequestType {

    private static final String TYPE = "all";

    private final ReportRepository reportRepository;
    private final ReportAssembler reportAssembler;

    public AllReportsRequestType(ReportRepository reportRepository,
                                 ReportAssembler reportAssembler
    ) {
        this.reportRepository = reportRepository;
        this.reportAssembler = reportAssembler;
    }

    @Override
    public boolean isSupport(String type) {
        return StringUtils.equalsIgnoreCase(TYPE, type);
    }

    @Override
    public Object execute(String username, Pageable pageable) {
        Page<Report> reports = reportRepository
            .findReportsByUsername(username, pageable);

        return reportAssembler.of(sort(reports));
    }
}
