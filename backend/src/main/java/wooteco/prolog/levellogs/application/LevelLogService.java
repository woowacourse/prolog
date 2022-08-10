package wooteco.prolog.levellogs.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.levellogs.application.dto.LevelLogRequest;
import wooteco.prolog.levellogs.application.dto.LevelLogResponse;
import wooteco.prolog.levellogs.application.dto.LevelLogSummariesResponse;
import wooteco.prolog.levellogs.application.dto.LevelLogSummaryResponse;
import wooteco.prolog.levellogs.application.dto.SelfDiscussionRequest;
import wooteco.prolog.levellogs.domain.LevelLog;
import wooteco.prolog.levellogs.domain.SelfDiscussion;
import wooteco.prolog.levellogs.domain.repository.LevelLogRepository;
import wooteco.prolog.levellogs.domain.repository.SelfDiscussionRepository;
import wooteco.prolog.levellogs.exception.InvalidLevelLogAuthorException;
import wooteco.prolog.levellogs.exception.LevelLogNotFoundException;
import wooteco.prolog.levellogs.exception.SelfDiscussionNotFoundException;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.member.exception.MemberNotFoundException;

@Service
@Transactional(readOnly = true)
public class LevelLogService {

    private final MemberRepository memberRepository;
    private final LevelLogRepository levelLogRepository;
    private final SelfDiscussionRepository selfDiscussionRepository;

    public LevelLogService(MemberRepository memberRepository,
                           LevelLogRepository levelLogRepository,
                           SelfDiscussionRepository selfDiscussionRepository) {
        this.memberRepository = memberRepository;
        this.levelLogRepository = levelLogRepository;
        this.selfDiscussionRepository = selfDiscussionRepository;
    }

    @Transactional
    public void insertLevellogs(Long memberId, LevelLogRequest levelLogRequest) {
        final Member member = memberRepository.findById(memberId)
            .orElseThrow(MemberNotFoundException::new);

        final LevelLog levelLog = levelLogRepository.save(
            new LevelLog(levelLogRequest.getTitle(), levelLogRequest.getContent(), member));

        insertSelfDiscussions(levelLogRequest, levelLog);
    }

    private void insertSelfDiscussions(LevelLogRequest levelLogRequest, LevelLog levelLog) {
        for (SelfDiscussionRequest selfDiscussionRequest : levelLogRequest.getLevelLogs()) {
            insertSelfDiscussion(levelLog, selfDiscussionRequest);
        }
    }

    private void insertSelfDiscussion(LevelLog levelLog,
                                      SelfDiscussionRequest selfDiscussionRequest) {
        selfDiscussionRepository.save(
            new SelfDiscussion(levelLog, selfDiscussionRequest.getAnswer(),
                selfDiscussionRequest.getQuestion()));
    }

    public LevelLogSummariesResponse findAll(Pageable pageable) {
        Page<LevelLog> page = levelLogRepository.findAll(pageable);

        List<LevelLogSummaryResponse> data = page.getContent()
            .stream()
            .map(LevelLogSummaryResponse::new)
            .collect(Collectors.toList());

        return new LevelLogSummariesResponse(data, page.getTotalElements(), page.getTotalPages(),
            page.getNumber() + 1);
    }

    @Transactional
    public void deleteById(Long memberId, Long levelLogId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(MemberNotFoundException::new);
        LevelLog levelLog = findById(levelLogId);

        if (!levelLog.isAuthor(member)) {
            throw new InvalidLevelLogAuthorException();
        }

        selfDiscussionRepository.deleteByLevelLog(levelLog);
        levelLogRepository.delete(levelLog);
    }

    @Transactional
    public void updateLevelLog(Long memberId, Long levelLogId, LevelLogRequest levelLogRequest) {
        final LevelLog levelLog = findById(levelLogId);
        levelLog.validateBelongTo(memberId);

        final List<SelfDiscussion> selfDiscussions = selfDiscussionRepository.findByLevelLog(
            levelLog);

        if (selfDiscussions.isEmpty()) {
            throw new SelfDiscussionNotFoundException();
        }

        levelLog.update(levelLogRequest.getTitle(), levelLogRequest.getContent());
        updateSelfDiscussion(levelLogRequest, selfDiscussions);
    }

    private void updateSelfDiscussion(LevelLogRequest levelLogRequest,
                                      List<SelfDiscussion> selfDiscussions) {
        final List<SelfDiscussionRequest> selfDiscussionRequests = levelLogRequest.getLevelLogs();

        for (int i = 0; i < selfDiscussionRequests.size(); i++) {
            final SelfDiscussionRequest updateData = selfDiscussionRequests.get(i);
            selfDiscussions.get(i).update(updateData.getAnswer(), updateData.getQuestion());
        }
    }

    public LevelLogResponse findLevelLogResponseById(Long id) {
        final LevelLog levelLog = findById(id);
        final List<SelfDiscussion> discussions = selfDiscussionRepository.findByLevelLog(levelLog);
        return new LevelLogResponse(levelLog, discussions);
    }

    public LevelLog findById(Long levelLogId) {
        return levelLogRepository.findById(levelLogId)
            .orElseThrow(LevelLogNotFoundException::new);
    }
}
