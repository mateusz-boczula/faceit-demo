package pl.boczula.mateusz.faceitdemo.dto.validation;

import java.util.Objects;

public class ValidationFailure {

    private String property;
    private String message;

    public ValidationFailure() {
    }

    public ValidationFailure(String property, String message) {
        this.property = property;
        this.message = message;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValidationFailure that = (ValidationFailure) o;
        return property.equals(that.property) &&
                message.equals(that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(property, message);
    }

    @Override
    public String toString() {
        return "ValidationFailureDto{" +
                "property='" + property + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
