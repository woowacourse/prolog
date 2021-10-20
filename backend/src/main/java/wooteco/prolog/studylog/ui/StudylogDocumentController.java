package wooteco.prolog.studylog.ui;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.studylog.application.DocumentService;
import wooteco.prolog.studylog.application.dto.ElasticHealthResponse;
import wooteco.prolog.studylog.infrastructure.dto.ClusterHealthDto;
import wooteco.prolog.studylog.infrastructure.dto.ClusterHealthDtos;
import wooteco.prolog.studylog.infrastructure.dto.IndexHealthDto;
import wooteco.prolog.studylog.infrastructure.dto.IndexHealthDtos;

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
    public ResponseEntity<ClusterHealthDtos> checkHealthOfCluster() {
        return ResponseEntity.ok(studylogDocumentService.checkHealthOfCluster());
    }

    @GetMapping("/health/index")
    public ResponseEntity<IndexHealthDtos> checkHealthOfIndex() {
        return ResponseEntity.ok(studylogDocumentService.checkHealthOfIndex());
    }

}
