package wooteco.prolog.common;

import org.springframework.context.annotation.Import;
import wooteco.prolog.config.ElasticSearchTestConfig;

@Import(ElasticSearchTestConfig.class)
public class ElasticServiceTest extends ServiceTest {

}
