package codingdojo;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageEnricherTest {

    private SpreadsheetWorkbook worksheet;

    @BeforeEach
    void setup() {
        worksheet = new SpreadsheetWorkbook();
    }

    @Test
    void noEnrichmentNeeded() {
        Exception e = new RuntimeException("Terrible problem");
        Approvals.verify(MessageEnricher.enrichError(worksheet, e));
    }

    @Test
    void circularReference() {
        Exception e = new SpreadsheetException("Circular Reference", Arrays.asList("C4", "C5"), null);
        Approvals.verify(MessageEnricher.enrichError(worksheet, e));
    }

    @Test
    void noMatchesFound() {
        Exception e = new SpreadsheetException("No matches found", null, "Missing Token");
        Approvals.verify(MessageEnricher.enrichError(worksheet, e));
    }

    @Test
    void circularReferenceWrongException() {
        Exception e = new RuntimeException("Circular Reference");
        Approvals.verify(MessageEnricher.enrichError(worksheet, e));
    }

    @Test
    void noMatchesFoundWrongException() {
        Exception e = new RuntimeException("No matches found");
        Approvals.verify(MessageEnricher.enrichError(worksheet, e));
    }

    @Test
    void expressionParseException() {
        Exception e = new ExpressionParseException();
        Approvals.verify(MessageEnricher.enrichError(worksheet, e));
    }

    @Test
    void objectReferenceNotSet() {
        Exception e = vLookup();
        Approvals.verify(MessageEnricher.enrichError(worksheet, e));
    }

    private Exception vLookup() {
        return new RuntimeException("Object reference not set to an instance of an object");
    }

    @Test
    void objectReferenceNotSetButStacktraceNotVLookup() {
        Exception e = new RuntimeException("Object reference not set to an instance of an object");
        Approvals.verify(MessageEnricher.enrichError(worksheet, e));
    }
}
