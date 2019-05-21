package codingdojo;

public class Normal extends ErrorResult {

    private final String message;

    protected Normal(Exception exception, String formulaName, String presentation, String message) {
        super(exception, formulaName, presentation);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
