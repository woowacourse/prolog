package wooteco.prolog.studylog.application.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StudylogDocumentPagingDto {

    private List<Long> studylogIds;
    private long totalSize;
    private int totalPage;
    private int currPage;

    public static StudylogDocumentPagingDto of(List<Long> studylogIds, long totalSize, int totalPage, int currPage) {
        return new StudylogDocumentPagingDto(studylogIds, totalSize, totalPage, currPage);
    }
}
