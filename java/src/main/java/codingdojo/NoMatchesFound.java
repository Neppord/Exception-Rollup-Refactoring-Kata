package codingdojo;

public class NoMatchesFound extends ErrorResult {
    protected NoMatchesFound(Exception exception, String formulaName, String presentation) {
        super(exception, formulaName, presentation);
    }
}
