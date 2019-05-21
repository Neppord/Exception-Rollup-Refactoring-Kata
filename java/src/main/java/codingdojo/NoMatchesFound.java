package codingdojo;

public class NoMatchesFound extends ErrorResult {
    protected NoMatchesFound(Exception exception, String formulaName, String presentation) {
        super(exception, formulaName, presentation);
    }

    private static String parseNoMatchException(Exception e, String formulaName) {
        if (e instanceof SpreadsheetException) {
            SpreadsheetException we = (SpreadsheetException) e;
            return "No match found for token [" + we.getToken() + "] related to formula '" + formulaName + "'.";
        }
        return e.getMessage();
    }

    @Override
    public String getMessage() {
        return parseNoMatchException(exception, formulaName);
    }
}
