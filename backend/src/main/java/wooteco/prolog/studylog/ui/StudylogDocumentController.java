package wooteco.prolog.studylog.ui;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.studylog.application.DocumentService;

@RestController
@AllArgsConstructor
public class StudylogDocumentController {

    private DocumentService studylogDocumentService;

    @GetMapping("/sync")
    public ResponseEntity<Void> sync() {
        studylogDocumentService.sync();
        return ResponseEntity.ok().build();
    }

}
