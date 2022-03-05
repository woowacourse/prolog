package wooteco.prolog.session.application;

import static java.util.stream.Collectors.toList;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.session.application.dto.LevelRequest;
import wooteco.prolog.session.application.dto.LevelResponse;
import wooteco.prolog.session.domain.Level;
import wooteco.prolog.session.domain.repository.LevelRepository;
import wooteco.prolog.studylog.exception.DuplicateLevelException;
import wooteco.prolog.studylog.exception.LevelNotFoundException;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class LevelService {

    private LevelRepository levelRepository;

    @Transactional
    public LevelResponse create(LevelRequest levelRequest) {
        validateName(levelRequest.getName());

        return LevelResponse.of(levelRepository.save(levelRequest.toEntity()));
    }

    private void validateName(String name) {
        if (levelRepository.findByName(name).isPresent()) {
            throw new DuplicateLevelException();
        }
    }

    public Level findById(Long id) {
        return levelRepository.findById(id)
            .orElseThrow(LevelNotFoundException::new);
    }

    public List<LevelResponse> findAll() {
        return levelRepository.findAll().stream()
            .map(LevelResponse::of)
            .collect(toList());
    }
}
