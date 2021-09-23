package wooteco.support.security.access;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;

public class ExpressionUtils {

    public static boolean evaluateAsBoolean(Expression expr, EvaluationContext ctx) {
        try {
            return expr.getValue(ctx, Boolean.class);
        } catch (EvaluationException e) {
            throw new IllegalArgumentException("Failed to evaluate expression '"
                + expr.getExpressionString() + "'", e);
        }
    }
}
