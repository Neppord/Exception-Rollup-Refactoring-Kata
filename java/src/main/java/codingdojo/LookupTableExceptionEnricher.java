package codingdojo;

import java.util.Optional;

public class LookupTableExceptionEnricher implements ExceptionEnricher{

    @Override
    public Optional<String> getEnrichedMessage(Exception e, String formulaName) {
        if ("Object reference not set to an instance of an object".equals(e.getMessage())
            && stackTraceContains(e, "vLookup")) {
            return Optional.of("Missing Lookup Table");
        } else {
            return Optional.empty();
        }
    }

    private boolean stackTraceContains(Exception e, String message) {
        for (StackTraceElement ste : e.getStackTrace()) {
            if (ste.getMethodName().contains(message)) {
                return true;
            }
        }
        return false;
    }
}
