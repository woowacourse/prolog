package wooteco.prolog.report.application.dto.response;

import java.util.List;

public class SimpleReportPageableResponse {
    private List<SimpleReportResponse> reports;
    private Long totalSize;
    private int totalPage;
    private int currentPage;

    public SimpleReportPageableResponse(List<SimpleReportResponse> reports,
                                        Long totalSize,
                                        int totalPage,
                                        int currentPage) {
        this.reports = reports;
        this.totalSize = totalSize;
        this.totalPage = totalPage;
        this.currentPage = currentPage;
    }

    public List<SimpleReportResponse> getReports() {
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
