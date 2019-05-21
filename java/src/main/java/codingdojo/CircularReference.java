package codingdojo;

public class CircularReference extends ErrorResult {
    protected CircularReference(Exception exception, String formulaName, String presentation) {
        super(exception, formulaName, presentation);
    }
}
