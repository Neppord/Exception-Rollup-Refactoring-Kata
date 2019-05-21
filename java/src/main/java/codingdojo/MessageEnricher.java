package codingdojo;

import java.util.Arrays;

public class MessageEnricher {

    public ErrorResult enrichError(SpreadsheetWorkbook spreadsheetWorkbook, Exception e) {

        String formulaName = spreadsheetWorkbook.getFormulaName();
        String presentation = spreadsheetWorkbook.getPresentation();
        String error = getErrorMessage(e, formulaName);
        return new ErrorResult(formulaName, error, presentation);
    }

    private String getErrorMessage(Exception e, String formulaName) {
        String error = e.getMessage();
        if (e instanceof ExpressionParseException) {
            error = "Invalid expression found in tax formula [" + formulaName + "]. Check that separators and delimiters use the English locale.";
        } else if (error.startsWith("Circular Reference") && e instanceof SpreadsheetException) {
            SpreadsheetException we = (SpreadsheetException) e;
            error = "Circular Reference in spreadsheet related to formula '" + formulaName + "'. Cells: " + we.getCells();
        } else if ("Object reference not set to an instance of an object".equals(error)
            && stackTraceContains(e, "vLookup")) {
            error = "Missing Lookup Table";
        } else if ("No matches found".equals(error) && e instanceof SpreadsheetException) {
            SpreadsheetException we = (SpreadsheetException) e;
            error = "No match found for token [" + we.getToken() + "] related to formula '" + formulaName + "'.";
        }
        return error;
    }

    private boolean stackTraceContains(Exception e, String message) {
        return Arrays.stream(e.getStackTrace())
            .anyMatch(ste -> ste.getMethodName().contains(message))
        ;
    }

}
