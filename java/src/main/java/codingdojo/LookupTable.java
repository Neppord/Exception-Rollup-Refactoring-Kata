package codingdojo;

public class LookupTable extends ErrorResult {
    protected LookupTable(Exception exception, String formulaName, String presentation) {
        super(exception, formulaName, presentation);
    }

    @Override
    public String getMessage() {
        return "Missing Lookup Table";
    }
}
