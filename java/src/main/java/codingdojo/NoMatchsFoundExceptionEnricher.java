package codingdojo;

import java.util.Optional;

public class NoMatchsFoundExceptionEnricher implements ExceptionEnricher{

    @Override
    public Optional<String> getEnrichedMessage(Exception e, String formulaName) {
        if ("No matches found".equals(e.getMessage())) {
            return Optional.of(parseNoMatchException(e, formulaName));
        } else  {
            return Optional.empty();
        }
    }

    private String parseNoMatchException(Exception e, String formulaName) {
        if (e instanceof SpreadsheetException) {
            SpreadsheetException we = (SpreadsheetException) e;
            return "No match found for token [" + we.getToken() + "] related to formula '" + formulaName + "'.";
        }
        return e.getMessage();
    }
}
