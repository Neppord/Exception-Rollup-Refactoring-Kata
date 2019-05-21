package codingdojo;

import java.util.Optional;

public class CircularReferenceEnricher implements ExceptionEnricher{

    @Override
    public Optional<String> getEnrichedMessage(Exception e, String formulaName) {
        if (!e.getMessage().startsWith("Circular Reference")) {
            return Optional.empty();
        }
        if (e instanceof SpreadsheetException) {
            SpreadsheetException we = (SpreadsheetException) e;
            return Optional.of("Circular Reference in spreadsheet related to formula '" + formulaName + "'. Cells: " + we.getCells());
        }
        return Optional.ofNullable(e.getMessage());
    }
}
