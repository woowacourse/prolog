package wooteco.prolog.levellogs.application;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.levellogs.application.dto.LevelLogResponse;
import wooteco.prolog.levellogs.application.dto.LevelLogSummariesResponse;
import wooteco.prolog.levellogs.application.dto.LevelLogSummaryResponse;
import wooteco.prolog.levellogs.domain.LevelLog;
import wooteco.prolog.levellogs.domain.SelfDiscussion;
import wooteco.prolog.levellogs.domain.repository.LevelLogRepository;
import wooteco.prolog.levellogs.domain.repository.SelfDiscussionRepository;
import wooteco.prolog.levellogs.exception.InvalidLevelLogAuthorException;
import wooteco.prolog.levellogs.exception.LevelLogNotFoundException;
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

    public LevelLogResponse findById(Long id) {
        final LevelLog levelLog = levelLogRepository.findById(id).orElseThrow(LevelLogNotFoundException::new);
        final List<SelfDiscussion> discussions = selfDiscussionRepository.findByLevelLog(levelLog);
        return new LevelLogResponse(levelLog, discussions);
    }

    public LevelLogSummariesResponse findAll(Pageable pageable) {
        Page<LevelLog> page = levelLogRepository.findAll(pageable);

        List<LevelLogSummaryResponse> data = page.getContent()
                .stream()
                .map(LevelLogSummaryResponse::new)
                .collect(Collectors.toList());

        return new LevelLogSummariesResponse(data, page.getTotalElements(), page.getTotalPages(), page.getNumber() + 1);
    }

    public void deleteById(Long memberId, Long levelLogId) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        LevelLog levelLog = levelLogRepository.findById(levelLogId).orElseThrow(LevelLogNotFoundException::new);

        if (!levelLog.isAuthor(member)) {
            throw new InvalidLevelLogAuthorException();
        }

        selfDiscussionRepository.deleteByLevelLog(levelLog);
        levelLogRepository.delete(levelLog);
    }
}
