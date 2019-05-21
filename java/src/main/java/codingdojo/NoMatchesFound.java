package codingdojo;

public class NoMatchesFound extends ErrorResult {

    private String token;

    protected NoMatchesFound(String formulaName, String presentation, String token) {
        super(formulaName, presentation);
        this.token = token;
    }

    @Override
    public String getMessage() {
        return "No match found for token [" + token + "] related to formula '" + formulaName + "'.";
    }
}
