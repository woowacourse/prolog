package wooteco.prolog.studylog.ui;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.studylog.application.DocumentService;
import wooteco.prolog.studylog.infrastructure.dto.OverallHealthDto;

@RestController
@AllArgsConstructor
public class StudylogDocumentController {

    private DocumentService studylogDocumentService;

    @GetMapping("/sync")
    public ResponseEntity<Void> sync() {
        studylogDocumentService.sync();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/health/overall")
    public ResponseEntity<List<OverallHealthDto>> healthCheck() {
//        ElasticHealthResponse elasticHealthResponse = studylogDocumentService.healthCheck();
        return ResponseEntity.ok(studylogDocumentService.healthCheck());
    }



}
