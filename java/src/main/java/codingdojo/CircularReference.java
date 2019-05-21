package codingdojo;

public class CircularReference extends ErrorResult {
    protected CircularReference(Exception exception, String formulaName, String presentation) {
        super(exception, formulaName, presentation);
    }

    private static String parseCircularReferenceException(Exception e, String formulaName) {
        if (e instanceof SpreadsheetException) {
            SpreadsheetException we = (SpreadsheetException) e;
            return "Circular Reference in spreadsheet related to formula '" + formulaName + "'. Cells: " + we.getCells();
        }
        return e.getMessage();
    }

    @Override
    public String getMessage() {
        return parseCircularReferenceException(exception, formulaName);
    }
}
