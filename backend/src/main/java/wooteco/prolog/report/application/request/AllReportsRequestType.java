package wooteco.prolog.report.application.request;

import static java.util.stream.Collectors.toList;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.testcontainers.shaded.org.apache.commons.lang.StringUtils;
import wooteco.prolog.report.application.dto.report.ReportAssembler;
import wooteco.prolog.report.domain.ReportRepository;

@Component
public class AllReportsRequestType implements ReportsRequestType {

    private static final String TYPE = "all";

    private final ReportRepository reportRepository;
    private final ReportAssembler reportAssembler;

    public AllReportsRequestType(ReportRepository reportRepository, ReportAssembler reportAssembler) {
        this.reportRepository = reportRepository;
        this.reportAssembler = reportAssembler;
    }

    @Override
    public boolean isSupport(String type) {
        return StringUtils.equalsIgnoreCase(TYPE, type);
    }

    @Override
    public Object execute(String username, Pageable pageable) {
        return reportRepository.findReportsByUsername(username, pageable).stream()
            .map(reportAssembler::of)
            .collect(toList());
    }
}
