package codingdojo;

public class Normal extends ErrorResult {
    protected Normal(Exception exception, String formulaName, String presentation) {
        super(exception, formulaName, presentation);
    }
}