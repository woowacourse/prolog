package wooteco.prolog.studylog.application;

import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import wooteco.prolog.studylog.application.dto.report.ReportResponse;
import wooteco.prolog.studylog.domain.repository.ReportRepository;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

}
