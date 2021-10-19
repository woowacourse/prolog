package wooteco.prolog;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileDirectoryController {

    private static final String LOG_DIR = System.getProperty("user.dir")+"/logs/performance";

    @GetMapping("/logs/directory")
    public String logDirectory() {
        return LOG_DIR;
    }

    @GetMapping("/logs/load")
    public String logs() throws IOException {
        final BufferedReader bufferedReader = new BufferedReader(
            new FileReader(LOG_DIR + "/performance.log"));

        return bufferedReader.lines().collect(Collectors.joining("\r\n"));
    }
}
