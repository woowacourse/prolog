package wooteco.prolog.studylog.ui;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.studylog.application.SearchDocumentService;

@RestController
@AllArgsConstructor
public class StudylogDocumentController {

    private SearchDocumentService searchDocumentService;

    @GetMapping("/sync")
    public ResponseEntity<Void> sync() {
        searchDocumentService.sync();
        return ResponseEntity.ok().build();
    }

}
