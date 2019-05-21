package codingdojo;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ErrorResultTest {

    private SpreadsheetWorkbook worksheet;

    @BeforeEach
    void setup() {
        worksheet = new SpreadsheetWorkbook();
    }

    @Test
    void noEnrichmentNeeded() {
        Exception e = new RuntimeException("Terrible problem");
        Approvals.verify(ErrorResult.enrichError(worksheet, e));
    }

    @Test
    void circularReference() {
        Exception e = new SpreadsheetException("Circular Reference", Arrays.asList("C4", "C5"), null);
        Approvals.verify(ErrorResult.enrichError(worksheet, e));
    }

    @Test
    void noMatchesFound() {
        Exception e = new SpreadsheetException("No matches found", null, "Missing Token");
        Approvals.verify(ErrorResult.enrichError(worksheet, e));
    }

    @Test
    void circularReferenceWrongException() {
        Exception e = new RuntimeException("Circular Reference");
        Approvals.verify(ErrorResult.enrichError(worksheet, e));
    }

    @Test
    void noMatchesFoundWrongException() {
        Exception e = new RuntimeException("No matches found");
        Approvals.verify(ErrorResult.enrichError(worksheet, e));
    }

    @Test
    void expressionParseException() {
        Exception e = new ExpressionParseException();
        Approvals.verify(ErrorResult.enrichError(worksheet, e));
    }

    @Test
    void objectReferenceNotSet() {
        Exception e = vLookup();
        Approvals.verify(ErrorResult.enrichError(worksheet, e));
    }

    private Exception vLookup() {
        return new RuntimeException("Object reference not set to an instance of an object");
    }

    @Test
    void objectReferenceNotSetButStacktraceNotVLookup() {
        Exception e = new RuntimeException("Object reference not set to an instance of an object");
        Approvals.verify(ErrorResult.enrichError(worksheet, e));
    }
}
