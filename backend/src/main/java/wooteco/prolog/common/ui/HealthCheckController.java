package wooteco.prolog.common.ui;

import static com.google.common.collect.ImmutableMap.of;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    private static final ImmutableMap<String, String> HEALTH_CHECK_RESULT = of("status", "UP");

    @GetMapping("/elb-health")
    public Map<String, String> healthCheck() {
        return HEALTH_CHECK_RESULT;
    }
}
