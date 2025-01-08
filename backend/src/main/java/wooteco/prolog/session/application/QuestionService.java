package wooteco.prolog.session.application;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wooteco.prolog.session.domain.Question;
import wooteco.prolog.session.domain.repository.QuestionRepository;

@AllArgsConstructor
@Service
public class QuestionService {

    private QuestionRepository questionRepository;

    public List<Question> findByIds(List<Long> questionIds) {
        return questionRepository.findAllById(questionIds);
    }

    public List<Question> findQuestionsByMissionId(Long missionId) {
        return questionRepository.findByMissionId(missionId);
    }
}
