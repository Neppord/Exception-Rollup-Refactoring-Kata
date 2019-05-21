package codingdojo;

public class MessageEnricher {
    private final Exception exception;
    private final String formulaName;
    private final String presentation;

    protected MessageEnricher(Exception exception, String formulaName, String presentation) {
        this.exception = exception;
        this.formulaName = formulaName;
        this.presentation = presentation;
    }

    public static MessageEnricher enrichError(SpreadsheetWorkbook spreadsheetWorkbook, Exception e) {

        String formulaName = spreadsheetWorkbook.getFormulaName();
        String error = getMessage(e, formulaName);
        return new MessageEnricher(e, formulaName, spreadsheetWorkbook.getPresentation());
    }

    private static String getMessage(Exception e, String formulaName) {
        String error;
        if (e instanceof ExpressionParseException) {
            error = "Invalid expression found in tax formula [" + formulaName + "]. Check that separators and delimiters use the English locale.";
        } else if (e.getMessage().startsWith("Circular Reference")) {
            error = parseCircularReferenceException(e, formulaName);
        } else if ("Object reference not set to an instance of an object".equals(e.getMessage())
            && stackTraceContains(e, "vLookup")) {
            error = "Missing Lookup Table";
        } else if ("No matches found".equals(e.getMessage())) {
            error = parseNoMatchException(e, formulaName);
        } else {
            error = e.getMessage();
        }
        return error;
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
        return getMessage(exception, formulaName);
    }

    public String getPresentation() {
        return presentation;
    }
}
