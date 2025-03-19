package wooteco.prolog.session.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.session.application.dto.QuestionRequest;
import wooteco.prolog.session.application.dto.QuestionResponse;
import wooteco.prolog.session.domain.Question;
import wooteco.prolog.session.domain.repository.MissionRepository;
import wooteco.prolog.session.domain.repository.QuestionRepository;

import java.util.List;

import static wooteco.prolog.common.exception.BadRequestCode.MISSION_NOT_FOUND;
import static wooteco.prolog.common.exception.BadRequestCode.QUESTION_NOT_FOUND;

@AllArgsConstructor
@Service
public class QuestionService {

    private QuestionRepository questionRepository;

    private MissionRepository missionRepository;

    public List<Question> findByIds(List<Long> questionIds) {
        return questionRepository.findAllById(questionIds);
    }

    public List<Question> findQuestionsByMissionId(Long missionId) {
        return questionRepository.findByMissionId(missionId);
    }

    @Transactional
    public QuestionResponse create(final QuestionRequest questionRequest) {
        final var mission = missionRepository.findById(questionRequest.missionId())
            .orElseThrow(() -> new BadRequestException(MISSION_NOT_FOUND));
        final var question = questionRepository.save(new Question(questionRequest.content(), mission));

        return new QuestionResponse(question.getId(), question.getContent());
    }

    @Transactional(readOnly = true)
    public QuestionResponse findById(final Long questionId) {
        return questionRepository.findById(questionId)
            .map(it -> new QuestionResponse(it.getId(), it.getContent()))
            .orElseThrow(() -> new BadRequestException(QUESTION_NOT_FOUND));
    }
}
