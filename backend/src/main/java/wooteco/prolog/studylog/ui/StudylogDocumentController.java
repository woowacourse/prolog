package wooteco.prolog.studylog.ui;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.studylog.application.DocumentService;
import wooteco.prolog.studylog.application.dto.ElasticHealthResponse;
import wooteco.prolog.studylog.application.dto.ClusterHealthResponses;
import wooteco.prolog.studylog.application.dto.IndexHealthResponses;

@RestController
@AllArgsConstructor
public class StudylogDocumentController {

    private DocumentService studylogDocumentService;

    @GetMapping("/sync")
    public ResponseEntity<Void> sync() {
        studylogDocumentService.sync();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/health")
    public ResponseEntity<ElasticHealthResponse> checkHealth() {
        return ResponseEntity.ok(studylogDocumentService.checkHealth());
    }

    @GetMapping("/health/cluster")
    public ResponseEntity<ClusterHealthResponses> checkHealthOfCluster() {
        return ResponseEntity.ok(studylogDocumentService.checkHealthOfCluster());
    }

    @GetMapping("/health/index")
    public ResponseEntity<IndexHealthResponses> checkHealthOfIndex() {
        return ResponseEntity.ok(studylogDocumentService.checkHealthOfIndex());
    }

}
