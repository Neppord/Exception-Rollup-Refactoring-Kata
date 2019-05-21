package codingdojo;

public class ErrorResult {
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
        } else if (exception.getMessage().startsWith("Circular Reference")) {
            return new CircularReference(exception, formulaName, spreadsheetWorkbook.getPresentation());
        } else if ("Object reference not set to an instance of an object".equals(exception.getMessage()) && stackTraceContains(exception, "vLookup")) {
            return new LookupTable(exception, formulaName, spreadsheetWorkbook.getPresentation());
        } else if ("No matches found".equals(exception.getMessage())) {
            return new NoMatchesFound(exception, formulaName, spreadsheetWorkbook.getPresentation());
        } else {
            return new Normal(exception, formulaName, spreadsheetWorkbook.getPresentation());
        }
    }

    private static boolean stackTraceContains(Exception e, String message) {
        for (StackTraceElement ste : e.getStackTrace()) {
            if (ste.getMethodName().contains(message)) {
                return true;
            }
        }
        return false;
    }

    private static String parseNoMatchException(Exception e, String formulaName) {
        if (e instanceof SpreadsheetException) {
            SpreadsheetException we = (SpreadsheetException) e;
            return "No match found for token [" + we.getToken() + "] related to formula '" + formulaName + "'.";
        }
        return e.getMessage();
    }

    private static String parseCircularReferenceException(Exception e, String formulaName) {
        if (e instanceof SpreadsheetException) {
            SpreadsheetException we = (SpreadsheetException) e;
            return "Circular Reference in spreadsheet related to formula '" + formulaName + "'. Cells: " + we.getCells();
        }
        return e.getMessage();
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

    public String getMessage() {
        String error;
        if (exception instanceof ExpressionParseException) {
            error = "Invalid expression found in tax formula [" + formulaName + "]. Check that separators and delimiters use the English locale.";
        } else if (exception.getMessage().startsWith("Circular Reference")) {
            error = parseCircularReferenceException(exception, formulaName);
        } else if ("Object reference not set to an instance of an object".equals(exception.getMessage())
            && stackTraceContains(exception, "vLookup")) {
            error = "Missing Lookup Table";
        } else if ("No matches found".equals(exception.getMessage())) {
            error = parseNoMatchException(exception, formulaName);
        } else {
            error = exception.getMessage();
        }
        return error;
    }

    public String getPresentation() {
        return presentation;
    }
}