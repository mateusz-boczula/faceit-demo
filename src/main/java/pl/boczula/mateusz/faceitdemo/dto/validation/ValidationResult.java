package pl.boczula.mateusz.faceitdemo.dto.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ValidationResult {

    private static final ValidationResult SUCCESS_VALIDATION_RESULT = new ValidationResult(true, Collections.emptyList());
    private boolean success;
    private List<ValidationFailure> validationFailures = new ArrayList<>();

    public ValidationResult() {
    }

    public ValidationResult(boolean success, List<ValidationFailure> validationFailures) {
        this.success = success;
        this.validationFailures = validationFailures;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<ValidationFailure> getValidationFailures() {
        return validationFailures;
    }

    public void setValidationFailures(List<ValidationFailure> validationFailures) {
        this.validationFailures = validationFailures;
    }

    public static ValidationResult ok() {
        return SUCCESS_VALIDATION_RESULT;
    }

    @Override
    public String toString() {
        return "ValidationResultDto{" +
                "success=" + success +
                ", validationFailures=" + validationFailures +
                '}';
    }
}
