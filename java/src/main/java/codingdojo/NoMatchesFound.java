package codingdojo;

public class NoMatchesFound extends ErrorResult {
    protected NoMatchesFound(Exception exception, String formulaName, String presentation) {
        super(exception, formulaName, presentation);
    }

    @Override
    public String getMessage() {
        if (exception instanceof SpreadsheetException) {
            SpreadsheetException we = (SpreadsheetException) exception;
            return "No match found for token [" + we.getToken() + "] related to formula '" + formulaName + "'.";
        }
        return exception.getMessage();
    }
}
