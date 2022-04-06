package wooteco.support.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@DataJpaTest
@EnableJpaAuditing
public @interface RepositoryTest {

}
