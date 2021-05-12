package wooteco.studylog.log;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class StudyLogController {
    @PostMapping("/study-logs")
    public ResponseEntity<StudyLogResponse> create() {
        StudyLogResponse studyLogResponse = new StudyLogResponse(1L, "제목", "본문", "작성자이름");
        return ResponseEntity.created(URI.create("/study-logs/" + studyLogResponse.getId())).body(studyLogResponse);
    }
}
