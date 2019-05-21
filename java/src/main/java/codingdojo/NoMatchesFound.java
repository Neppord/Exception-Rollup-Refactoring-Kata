package codingdojo;

public class NoMatchesFound extends ErrorResult {

    private String token;

    protected NoMatchesFound(SpreadsheetException exception, String formulaName, String presentation) {
        super(formulaName, presentation);
        token = exception.getToken();
    }

    @Override
    public String getMessage() {
        return "No match found for token [" + token + "] related to formula '" + formulaName + "'.";
    }
}
