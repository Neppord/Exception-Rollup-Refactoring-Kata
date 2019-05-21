package codingdojo;

import java.util.Optional;
import java.util.function.Supplier;

public interface ExceptionEnricher {

    Optional<String> getEnrichedMessage(Exception e, String formulaName);

    default ExceptionEnricher or (ExceptionEnricher other) {
        return (e, formulaName) -> {
            Optional<String> result = getEnrichedMessage(e, formulaName);
            Supplier<Optional<? extends String>> closure = () -> other.getEnrichedMessage(e, formulaName);
            return result.or(closure);
        };
    }
}
