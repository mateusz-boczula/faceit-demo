package pl.boczula.mateusz.faceitdemo.dto.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ValidationFailuresBuilder<T> {

    private String property;
    private T value;
    private List<ValidationFailure> failures = new ArrayList<>();

    public static <T> ValidationFailuresBuilder<T> create(String property, T value) {
        final ValidationFailuresBuilder builder = new ValidationFailuresBuilder();
        builder.property = property;
        builder.value = value;
        return builder;
    }

    public ValidationFailuresBuilder<T> validate(Predicate<T> predicate, String message) {
        if(!predicate.test(value)) {
            failures.add(new ValidationFailure(property, message));
        }

        return this;
    }

    public List<ValidationFailure> build() {
        return failures;
    }

}
