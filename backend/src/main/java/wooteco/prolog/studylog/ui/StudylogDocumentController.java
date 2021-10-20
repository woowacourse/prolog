package wooteco.prolog.studylog.ui;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.studylog.application.DocumentService;
import wooteco.prolog.studylog.infrastructure.dto.ClusterHealthDto;
import wooteco.prolog.studylog.infrastructure.dto.IndexHealthDto;

@RestController
@AllArgsConstructor
public class StudylogDocumentController {

    private DocumentService studylogDocumentService;

    @GetMapping("/sync")
    public ResponseEntity<Void> sync() {
        studylogDocumentService.sync();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/health/cluster")
    public ResponseEntity<List<ClusterHealthDto>> checkHealthOfCluster() {
//        ElasticHealthResponse elasticHealthResponse = studylogDocumentService.healthCheck();
        return ResponseEntity.ok(studylogDocumentService.checkHealthOfCluster());
    }

    // TODO List 감싼 DTO 만들기
    @GetMapping("/health/index")
    public ResponseEntity<List<IndexHealthDto>> checkHealthOfIndex() {
//        ElasticHealthResponse elasticHealthResponse = studylogDocumentService.healthCheck();
        return ResponseEntity.ok(studylogDocumentService.checkHealthOfIndex());
    }

}
