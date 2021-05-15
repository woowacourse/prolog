package wooteco.studylog.log;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@RestController
public class StudyLogController {
    @PostMapping("/study-logs")
    public ResponseEntity<StudyLogResponse> create() {
        StudyLogResponse studyLogResponse = new StudyLogResponse(1L, "제목", "본문", "작성자이름");
        return ResponseEntity.created(URI.create("/study-logs/" + studyLogResponse.getId())).body(studyLogResponse);
    }

    @GetMapping("/study-logs")
    public ResponseEntity<List<StudyLogResponse>> list() {
        List<StudyLogResponse> studyLogResponses = Arrays.asList(
                new StudyLogResponse(1L, "제목1", "본문1", "작성자이름1"),
                new StudyLogResponse(2L, "제목2", "본문2", "작성자이름2")
        );
        return ResponseEntity.ok().body(studyLogResponses);
    }
}
