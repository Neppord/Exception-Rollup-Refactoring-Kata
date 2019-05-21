package codingdojo;

public class ExpressionParse extends ErrorResult {
    protected ExpressionParse(Exception exception, String formulaName, String presentation) {
        super(exception, formulaName, presentation);
    }

    @Override
    public String getMessage() {
        return "Invalid expression found in tax formula [" + formulaName + "]. Check that separators and delimiters use the English locale.";
    }
}
