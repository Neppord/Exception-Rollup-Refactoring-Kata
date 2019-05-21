package codingdojo;

public class Normal extends ErrorResult {

    private final String message;

    protected Normal(Exception exception, String formulaName, String presentation) {
        super(exception, formulaName, presentation);
        message = exception.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
