package codingdojo;

public class CircularReference extends ErrorResult {
    protected CircularReference(Exception exception, String formulaName, String presentation) {
        super(exception, formulaName, presentation);
    }

    @Override
    public String getMessage() {
        if (exception instanceof SpreadsheetException) {
            SpreadsheetException we = (SpreadsheetException) exception;
            return "Circular Reference in spreadsheet related to formula '" + formulaName + "'. Cells: " + we.getCells();
        }
        return exception.getMessage();
    }
}
