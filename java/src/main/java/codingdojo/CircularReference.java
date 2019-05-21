package codingdojo;

import java.util.List;

public class CircularReference extends ErrorResult {

    private List<String> cells;

    protected CircularReference(String formulaName, String presentation, List<String> cells) {
        super(formulaName, presentation);
        this.cells = cells;
    }

    @Override
    public String getMessage() {
        return "Circular Reference in spreadsheet related to formula '" + formulaName + "'. Cells: " + cells;
    }
}
