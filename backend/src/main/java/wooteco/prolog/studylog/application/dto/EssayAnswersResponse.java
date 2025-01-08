package wooteco.prolog.studylog.application.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import wooteco.prolog.roadmap.application.dto.EssayAnswerResponse;
import wooteco.prolog.roadmap.domain.EssayAnswer;

import java.util.List;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EssayAnswersResponse {

    private static final int ONE_INDEXED_PARAMETER = 1;

    private List<EssayAnswerResponse> data;
    private Long totalSize;
    private int totalPage;
    private int currPage;

    public static EssayAnswersResponse of(Page<EssayAnswer> page) {
        Page<EssayAnswerResponse> responsePage = new PageImpl<>(
            toResponses(page.getContent()),
            page.getPageable(),
            page.getTotalElements()
        );

        return new EssayAnswersResponse(responsePage.getContent(),
            responsePage.getTotalElements(),
            responsePage.getTotalPages(),
            responsePage.getNumber() + ONE_INDEXED_PARAMETER);
    }

    private static List<EssayAnswerResponse> toResponses(List<EssayAnswer> essayAnswers) {
        return essayAnswers.stream()
            .map(EssayAnswerResponse::of)
            .collect(toList());
    }
}
