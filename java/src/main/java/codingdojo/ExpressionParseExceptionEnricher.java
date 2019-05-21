package codingdojo;

import java.util.Optional;

public class ExpressionParseExceptionEnricher implements ExceptionEnricher {

    @Override
    public Optional<String> getEnrichedMessage(Exception e, String formulaName) {
        if(e instanceof ExpressionParseException) {
            return Optional.of("Invalid expression found in tax formula [" + formulaName + "]. Check that separators and delimiters use the English locale.");
        } else {
            return Optional.empty();
        }
    }
}
