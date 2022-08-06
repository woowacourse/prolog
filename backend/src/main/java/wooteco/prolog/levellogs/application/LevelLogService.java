package wooteco.prolog.levellogs.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.levellogs.application.dto.LevelLogResponse;
import wooteco.prolog.levellogs.application.dto.LevelLogsResponse;
import wooteco.prolog.levellogs.domain.LevelLog;
import wooteco.prolog.levellogs.domain.SelfDiscussion;
import wooteco.prolog.levellogs.domain.repository.LevelLogRepository;
import wooteco.prolog.levellogs.domain.repository.SelfDiscussionRepository;
import wooteco.prolog.levellogs.exception.LevelLogNotFoundException;

@Service
@Transactional(readOnly = true)
public class LevelLogService {

    private final LevelLogRepository levelLogRepository;
    private final SelfDiscussionRepository selfDiscussionRepository;

    public LevelLogService(LevelLogRepository levelLogRepository, SelfDiscussionRepository selfDiscussionRepository) {
        this.levelLogRepository = levelLogRepository;
        this.selfDiscussionRepository = selfDiscussionRepository;
    }

    public LevelLogResponse findById(Long id) {
        final LevelLog levelLog = levelLogRepository.findById(id).orElseThrow(LevelLogNotFoundException::new);
        final List<SelfDiscussion> discussions = selfDiscussionRepository.findByLevelLog(levelLog);
        return new LevelLogResponse(levelLog, discussions);
    }

    public LevelLogsResponse findAll(Pageable pageable) {
        Page<LevelLog> page = levelLogRepository.findAll(pageable);
        List<SelfDiscussion> selfDiscussions = selfDiscussionRepository.findAllByLevelLogIn(page.getContent());
        return mapToResponse(page, selfDiscussions);
    }

    private LevelLogsResponse mapToResponse(Page<LevelLog> page, List<SelfDiscussion> selfDiscussions) {
        Map<LevelLog, List<SelfDiscussion>> map = selfDiscussions.stream()
                .collect(Collectors.toMap(SelfDiscussion::getLevelLog, this::modifiableSingleList, this::mergeList));

        List<LevelLogResponse> response = page.getContent()
                .stream()
                .map(levelLog -> new LevelLogResponse(levelLog, map.get(levelLog)))
                .collect(Collectors.toList());

        return new LevelLogsResponse(response, page.getTotalElements(), page.getTotalPages(), page.getNumber() + 1);
    }

    private List<SelfDiscussion> modifiableSingleList(SelfDiscussion discussion) {
        final ArrayList<SelfDiscussion> list = new ArrayList<>();
        list.add(discussion);
        return list;
    }

    private List<SelfDiscussion> mergeList(final List<SelfDiscussion> a, final List<SelfDiscussion> b) {
        a.addAll(b);
        return a;
    }
}
