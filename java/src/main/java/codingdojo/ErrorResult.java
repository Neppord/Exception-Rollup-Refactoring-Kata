package codingdojo;

public class ErrorResult {
    private final String formulaName;
    private final String message;
    private final String presentation;

    public ErrorResult(String formulaName, String message, String presentation) {

        this.formulaName = formulaName;
        this.message = message;
        this.presentation = presentation;
    }

    public static ErrorResult enrichError(SpreadsheetWorkbook spreadsheetWorkbook, Exception e) {

        String formulaName = spreadsheetWorkbook.getFormulaName();

        if (e instanceof ExpressionParseException) {
            String error = "Invalid expression found in tax formula [" + formulaName + "]. Check that separators and delimiters use the English locale.";
            return new ErrorResult(formulaName, error, spreadsheetWorkbook.getPresentation());
        }
        if (e.getMessage().startsWith("Circular Reference")) {
            String error = parseCircularReferenceException(e, formulaName);
            return new ErrorResult(formulaName, error, spreadsheetWorkbook.getPresentation());
        }
        if ("Object reference not set to an instance of an object".equals(e.getMessage())
                && stackTraceContains(e,"vLookup")) {
            return new ErrorResult(formulaName, "Missing Lookup Table", spreadsheetWorkbook.getPresentation());
        }
        if ("No matches found".equals(e.getMessage())) {
            String error = parseNoMatchException(e, formulaName);
            return new ErrorResult(formulaName, error, spreadsheetWorkbook.getPresentation());

        }


        return new ErrorResult(formulaName, e.getMessage(), spreadsheetWorkbook.getPresentation());
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
                ", message='" + message + '\'' +
                ", presentation='" + presentation + '\'' +
                '}';
    }

    public String getFormulaName() {
        return formulaName;
    }

    public String getMessage() {
        return message;
    }

    public String getPresentation() {
        return presentation;
    }
}
