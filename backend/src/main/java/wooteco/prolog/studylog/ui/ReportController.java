package wooteco.prolog.studylog.ui;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.member.domain.Member;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @GetMapping("/{memberName}/{reportId}")
    public ResponseEntity<String> getSingleReport(
        @PathVariable String memberName,
        @PathVariable Long reportId
    ) {
        return ResponseEntity
            .status(200)
            .contentType(MediaType.APPLICATION_JSON)
            .body(reportResponse());
    }

    @PostMapping()
    public ResponseEntity<String> createReport() {
        return ResponseEntity
            .status(200)
            .contentType(MediaType.APPLICATION_JSON)
            .body(reportResponse());
    }

    public String reportResponse() {
        return " {\n"
            + "  \"id\": 1,\n"
            + "  \"title\": \"리포트1\",\n"
            + "  \"description\": \"리포트1 입니다.\",\n"
            + "  \"abilityGraph\": {\n"
            + "    \"abilities\": [ \n"
            + "      {\n"
            + "        \"id\": 1,\n"
            + "        \"name\": \"역량1\",\n"
            + "        \"weight\": 1,\n"
            + "        \"percentage\": 33.33,\n"
            + "        \"isPresent\": true\n"
            + "      },\n"
            + "      {\n"
            + "        \"id\": 2,\n"
            + "        \"name\": \"역량2\",\n"
            + "        \"weight\": 1,\n"
            + "        \"percentage\": 33.33,\n"
            + "        \"isPresent\": true\n"
            + "      },\n"
            + "      {\n"
            + "        \"id\": 3,\n"
            + "        \"name\": \"역량3\",\n"
            + "        \"weight\": 1,\n"
            + "        \"percentage\": 33.33,\n"
            + "        \"isPresent\": true\n"
            + "      }\n"
            + "    ]\n"
            + "  }\n"
            + " }";
    }

    @GetMapping("/{memberName}")
    public ResponseEntity<String> getReports(@PathVariable String memberName, String type) {
        if(type.equals("simple")) {

            return ResponseEntity
                .status(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body(simpleTypeReportResponse());
        } else {
            return ResponseEntity
                .status(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body(allReportResponses());
        }
    }

    private String simpleTypeReportResponse() {
        return "[\n"
            + "  {\n"
            + "    \"id\": 1,\n"
            + "    \"name\": \"리포트1\",\n"
            + "    \"isRepresent\": true\n"
            + "  },\n"
            + "  {\n"
            + "    \"id\": 2,\n"
            + "    \"name\": \"리포트2\",\n"
            + "    \"isRepresent\": false\n"
            + "  }\n"
            + "]\n";
    }

    private String allReportResponses() {
        return "[\n"
            + "  {\n"
            + "  \"id\": 1,\n"
            + "  \"title\": \"리포트1\",\n"
            + "  \"description\": \"리포트1 입니다.\",\n"
            + "  \"abilityGraph\": {\n"
            + "    \"abilities\": [ \n"
            + "      {\n"
            + "        \"id\": 1,\n"
            + "        \"name\": \"역량1\",\n"
            + "        \"weight\": 1,\n"
            + "        \"percentage\": 33.33,\n"
            + "        \"isPresent\": true\n"
            + "      },\n"
            + "      {\n"
            + "        \"id\": 2,\n"
            + "        \"name\": \"역량2\",\n"
            + "        \"weight\": 1,\n"
            + "        \"percentage\": 33.33,\n"
            + "        \"isPresent\": true\n"
            + "      },\n"
            + "      {\n"
            + "        \"id\": 3,\n"
            + "        \"name\": \"역량3\",\n"
            + "        \"weight\": 1,\n"
            + "        \"percentage\": 33.33,\n"
            + "        \"isPresent\": true\n"
            + "      }\n"
            + "    ]\n"
            + "  },\n"
            + "  \"studylogs\": [\n"
            + "    {\n"
            + "      \"id\":1,\n"
            + "      \"createAt\":\"2021-09-15T13:28:31.478048\",\n"
            + "      \"updateAt\":\"2021-09-15T13:28:31.478048\",\n"
            + "      \"title\": \"스터디로그1\",\n"
            + "      \"abilities\": [\n"
            + "        {\n"
            + "          \"id\":1,\n"
            + "          \"Name\":\"역량1\",\n"
            + "          \"color\":\"red\",\n"
            + "          \"isPerent\":true\n"
            + "        }\n"
            + "      ]\n"
            + "    },\n"
            + "    {\n"
            + "      \"id\":2,\n"
            + "      \"createAt\":\"2021-09-15T13:28:31.478048\",\n"
            + "      \"updateAt\":\"2021-09-15T13:28:31.478048\",\n"
            + "      \"title\": \"스터디로그2\",\n"
            + "      \"abilities\": [\n"
            + "        {\n"
            + "          \"id\":2,\n"
            + "          \"Name\":\"역량2\",\n"
            + "          \"color\":\"yellow\",\n"
            + "          \"isPerent\":true\n"
            + "        }\n"
            + "      ]\n"
            + "    }\n"
            + "  ],\n"
            + "  \"isRepresent\":true\n"
            + "}\n"
            + "]\n"
            + "\n"
            + "\n";
    }

    @PutMapping("/{reportId}")
    public ResponseEntity<String> putReport(
        @PathVariable Long reportId
    ) {
        return ResponseEntity
            .status(200)
            .contentType(MediaType.APPLICATION_JSON)
            .body(reportResponse());
    }

    @DeleteMapping("/{reportId}")
    public ResponseEntity<Void> deleteReport(
        @PathVariable Long reportId
    ) {
        return ResponseEntity
            .status(200)
            .contentType(MediaType.APPLICATION_JSON)
            .build();
    }
}
