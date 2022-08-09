package wooteco.prolog.levellogs.ui;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.levellogs.application.LevelLogService;
import wooteco.prolog.levellogs.application.dto.LevelLogRequest;
import wooteco.prolog.levellogs.application.dto.LevelLogResponse;
import wooteco.prolog.levellogs.application.dto.LevelLogSummariesResponse;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.ui.LoginMember;

@RestController
public class LevelLogsController {

    private final LevelLogService levelLogService;

    public LevelLogsController(LevelLogService levelLogService) {
        this.levelLogService = levelLogService;
    }

    public ResponseEntity<Void> create(@AuthMemberPrincipal LoginMember member,
                                       @RequestBody LevelLogRequest levelLogRequest) {
        levelLogService.insertLevellogs(member.getId(), levelLogRequest);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<LevelLogResponse> findById(Long id) {
        final LevelLogResponse response = levelLogService.findById(id);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<LevelLogSummariesResponse> findAll(Pageable pageable) {
        final LevelLogSummariesResponse response = levelLogService.findAll(pageable);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Void> deleteById(@AuthMemberPrincipal LoginMember member, Long id) {
        levelLogService.deleteById(member.getId(), id);
        return ResponseEntity.noContent().build();
    }
}
