package codingdojo;

public abstract class ErrorResult {
    protected final Exception exception;
    protected final String formulaName;
    protected final String presentation;

    protected ErrorResult(Exception exception, String formulaName, String presentation) {
        this.exception = exception;
        this.formulaName = formulaName;
        this.presentation = presentation;
    }

    public static ErrorResult enrichError(SpreadsheetWorkbook spreadsheetWorkbook, Exception exception) {
        String formulaName = spreadsheetWorkbook.getFormulaName();
        if (exception instanceof ExpressionParseException) {
            return new ExpressionParse(exception, formulaName, spreadsheetWorkbook.getPresentation());
        } else if (exception instanceof SpreadsheetException && exception.getMessage().startsWith("Circular Reference")) {
            return new CircularReference((SpreadsheetException) exception, formulaName, spreadsheetWorkbook.getPresentation());
        } else if ("Object reference not set to an instance of an object".equals(exception.getMessage()) && stackTraceContains(exception, "vLookup")) {
            return new LookupTable(exception, formulaName, spreadsheetWorkbook.getPresentation());
        } else if (exception instanceof SpreadsheetException && "No matches found".equals(exception.getMessage())) {
            return new NoMatchesFound((SpreadsheetException) exception, formulaName, spreadsheetWorkbook.getPresentation());
        } else {
            return new Normal(exception, formulaName, spreadsheetWorkbook.getPresentation(), exception.getMessage());
        }
    }

    protected static boolean stackTraceContains(Exception e, String message) {
        for (StackTraceElement ste : e.getStackTrace()) {
            if (ste.getMethodName().contains(message)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "ErrorResult{" +
                "formulaName='" + formulaName + '\'' +
                ", message='" + getMessage() + '\'' +
                ", presentation='" + presentation + '\'' +
                '}';
    }

    public String getFormulaName() {
        return formulaName;
    }

    public abstract String getMessage();

    public String getPresentation() {
        return presentation;
    }
}
