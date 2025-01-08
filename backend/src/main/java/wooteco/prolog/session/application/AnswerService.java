package wooteco.prolog.session.application;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wooteco.prolog.session.domain.Answer;
import wooteco.prolog.session.domain.AnswerTemp;
import wooteco.prolog.session.domain.Question;
import wooteco.prolog.session.domain.repository.AnswerRepository;
import wooteco.prolog.session.domain.repository.AnswerTempRepository;
import wooteco.prolog.studylog.application.dto.AnswerRequest;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.StudylogTemp;

@AllArgsConstructor
@Service
public class AnswerService {

    private QuestionService questionService;
    private AnswerRepository answerRepository;
    private AnswerTempRepository answerTempRepository;

    public List<Answer> saveAnswers(Long memberId, List<AnswerRequest> answerRequests, Studylog studylog) {
        List<Question> questions = questionService.findByIds(answerRequests.stream()
            .map(AnswerRequest::getQuestionId)
            .collect(Collectors.toList()));

        List<Answer> answers = answerRequests.stream()
            .map(answerRequest -> new Answer(studylog, findQuestionById(questions, answerRequest.getQuestionId()),
                memberId, answerRequest.getAnswerContent()))
            .collect(Collectors.toList());

        deleteAnswerTemp(memberId);
        return answerRepository.saveAll(answers);
    }

    public List<AnswerTemp> saveAnswerTemp(Long memberId, List<AnswerRequest> answerRequests,
                                           StudylogTemp studylogTemp) {
        List<Question> questions = questionService.findByIds(answerRequests.stream()
            .map(AnswerRequest::getQuestionId)
            .collect(Collectors.toList()));

        List<AnswerTemp> answers = answerRequests.stream()
            .map(answerRequest -> new AnswerTemp(studylogTemp,
                findQuestionById(questions, answerRequest.getQuestionId()),
                memberId, answerRequest.getAnswerContent()))
            .collect(Collectors.toList());

        deleteAnswerTemp(memberId);
        return answerTempRepository.saveAll(answers);
    }

    private void deleteAnswerTemp(Long memberId) {
        if (answerTempRepository.existsByMemberId(memberId)) {
            answerTempRepository.deleteByMemberId(memberId);
        }
    }

    private Question findQuestionById(List<Question> questions, Long questionId) {
        return questions.stream()
            .filter(it -> Objects.equals(it.getId(), questionId))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("해당하는 질문이 없습니다."));
    }

    public List<AnswerTemp> findAnswersTempByMemberId(Long memberId) {
        return answerTempRepository.findByMemberId(memberId);
    }

    public List<Answer> findAnswersByStudylogId(Long studylogId) {
        return answerRepository.findByStudylogId(studylogId);
    }

    public void updateAnswers(List<AnswerRequest> answerRequests, Studylog studylog) {
        List<Answer> answers = answerRepository.findByStudylogId(studylog.getId());

        answers.forEach(answer -> answerRequests.stream()
            .filter(it -> Objects.equals(it.getQuestionId(), answer.getQuestion().getId()))
            .findFirst()
            .ifPresent(it -> answer.updateContent(it.getAnswerContent())));
    }

    public Map<Long, List<Answer>> findAnswersByStudylogs(List<Studylog> studylogs) {
        List<Long> studylogIds = studylogs.stream()
            .map(Studylog::getId)
            .collect(Collectors.toList());

        return answerRepository.findByStudylogIdIn(studylogIds).stream()
            .collect(Collectors.groupingBy(answer -> answer.getStudylog().getId(), Collectors.toList()));
    }
}
