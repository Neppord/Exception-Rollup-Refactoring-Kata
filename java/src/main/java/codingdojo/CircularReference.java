package codingdojo;

import java.util.List;

public class CircularReference extends ErrorResult {

    private List<String> cells;

    protected CircularReference(SpreadsheetException exception, String formulaName, String presentation) {
        super(formulaName, presentation);
        cells = exception.getCells();
    }

    @Override
    public String getMessage() {
        return "Circular Reference in spreadsheet related to formula '" + formulaName + "'. Cells: " + cells;
    }
}
