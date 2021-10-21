package wooteco.prolog.report.application.dto.report.response;

import java.util.List;
import org.springframework.data.domain.Page;
import wooteco.prolog.report.domain.report.Report;

public class ReportPageableResponse {

    private List<ReportResponse> reports;
    private Long totalSize;
    private int totalPage;
    private int currentPage;

    public ReportPageableResponse(List<ReportResponse> reports,
                                  Long totalSize,
                                  int totalPage,
                                  int currentPage) {
        this.reports = reports;
        this.totalSize = totalSize;
        this.totalPage = totalPage;
        this.currentPage = currentPage;
    }

    public List<ReportResponse> getReports() {
        return reports;
    }

    public Long getTotalSize() {
        return totalSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }
}
