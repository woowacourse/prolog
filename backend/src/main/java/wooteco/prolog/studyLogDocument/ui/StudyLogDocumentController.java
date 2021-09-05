package wooteco.prolog.studyLogDocument.ui;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.studyLogDocument.application.StudyLogDocumentService;

@RestController
@AllArgsConstructor
public class StudyLogDocumentController {

    private StudyLogDocumentService studyLogDocumentService;

    @GetMapping("/sync")
    public ResponseEntity sync() {
        studyLogDocumentService.sync();
        return ResponseEntity.ok().build();
    }

}
