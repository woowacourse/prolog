package wooteco.prolog.report.application.report;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.testcontainers.shaded.org.apache.commons.lang.StringUtils;
import wooteco.prolog.report.application.dto.ReportAssembler;
import wooteco.prolog.report.domain.report.Report2;
import wooteco.prolog.report.domain.report.repository.ReportRepository2;

@Component
public class AllReportsRequestType implements ReportsRequestType {

    private static final String TYPE = "all";

    private final ReportRepository2 reportRepository;
    private final ReportAssembler reportAssembler;

    public AllReportsRequestType(ReportRepository2 reportRepository,
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
        Page<Report2> reports = reportRepository
            .findReportsByUsername(username, pageable);

        return reportAssembler.of(sort(reports));
    }
}
