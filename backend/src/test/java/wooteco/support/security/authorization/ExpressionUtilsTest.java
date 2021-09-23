package wooteco.support.security.authorization;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

class ExpressionUtilsTest {

    @Test
    void name() {
        Expression expression = new SpelExpressionParser().parseExpression("");
        EvaluationContext evaluationContext = new StandardEvaluationContext();
        boolean result = ExpressionUtils.evaluateAsBoolean(expression, evaluationContext);

        assertThat(result).isTrue();
    }
}