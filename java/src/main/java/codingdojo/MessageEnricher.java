package codingdojo;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MessageEnricher {

    public ErrorResult enrichError(SpreadsheetWorkbook spreadsheetWorkbook, Exception e) {

        String formulaName = spreadsheetWorkbook.getFormulaName();
        ExceptionEnricher ee = new ExpressionParseExceptionEnricher()
            .or(new CircularReferenceEnricher())
            .or(new LookupTableExceptionEnricher())
            .or(new NoMatchsFoundExceptionEnricher());
        Optional<String> enrichedMessage = ee.getEnrichedMessage(e, formulaName);
        String message = enrichedMessage.orElse(e.getMessage());
        return new ErrorResult(formulaName, message, spreadsheetWorkbook.getPresentation());
    }

}
