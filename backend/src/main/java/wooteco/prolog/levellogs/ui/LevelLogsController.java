package wooteco.prolog.levellogs.ui;

import java.net.URI;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.levellogs.application.LevelLogService;
import wooteco.prolog.levellogs.application.dto.LevelLogRequest;
import wooteco.prolog.levellogs.application.dto.LevelLogResponse;
import wooteco.prolog.levellogs.application.dto.LevelLogSummariesResponse;
import wooteco.prolog.login.aop.MemberOnly;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.ui.LoginMember;

@RestController
@RequestMapping("/levellogs")
public class LevelLogsController {

    private final LevelLogService levelLogService;

    public LevelLogsController(LevelLogService levelLogService) {
        this.levelLogService = levelLogService;
    }

    @PostMapping
    @MemberOnly
    public ResponseEntity<Void> create(@AuthMemberPrincipal LoginMember member,
                                       @RequestBody LevelLogRequest levelLogRequest) {
        final LevelLogResponse response = levelLogService.insertLevellogs(
            member.getId(), levelLogRequest);
        return ResponseEntity.created(URI.create("/levellogs/" + response.getId())).build();
    }

    @PutMapping("/{id}")
    @MemberOnly
    public ResponseEntity<Void> updateLovellog(@AuthMemberPrincipal LoginMember member,
                                               @PathVariable Long id,
                                               @RequestBody LevelLogRequest levelLogRequest) {
        levelLogService.updateLevelLog(member.getId(), id, levelLogRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LevelLogResponse> findById(@PathVariable Long id) {
        final LevelLogResponse response = levelLogService.findLevelLogResponseById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<LevelLogSummariesResponse> findAll(
        @PageableDefault(sort = {"id"}, direction = Direction.DESC) Pageable pageable) {
        final LevelLogSummariesResponse response = levelLogService.findAll(pageable);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @MemberOnly
    public ResponseEntity<Void> deleteById(@AuthMemberPrincipal LoginMember member,
                                           @PathVariable Long id) {
        levelLogService.deleteById(member.getId(), id);
        return ResponseEntity.noContent().build();
    }
}
