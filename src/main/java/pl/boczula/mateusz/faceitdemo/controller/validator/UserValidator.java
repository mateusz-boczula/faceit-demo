package pl.boczula.mateusz.faceitdemo.controller.validator;

import org.springframework.stereotype.Component;
import pl.boczula.mateusz.faceitdemo.dto.UserDto;
import pl.boczula.mateusz.faceitdemo.dto.validation.ValidationFailure;
import pl.boczula.mateusz.faceitdemo.dto.validation.ValidationFailuresBuilder;
import pl.boczula.mateusz.faceitdemo.dto.validation.ValidationResult;
import pl.boczula.mateusz.faceitdemo.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Component
public final class UserValidator {

    private final UserService userService;

    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    public ValidationResult validateCreate(UserDto userDto) {
        List<ValidationFailure> failures = new ArrayList<>();
        validateNullId(userDto, failures);
        validateCommon(userDto, failures);

        return new ValidationResult(failures.isEmpty(), failures);
    }

    public ValidationResult validateUpdate(UserDto userDto) {
        List<ValidationFailure> failures = new ArrayList<>();
        validateId(userDto, failures);
        validateCommon(userDto, failures);

        return new ValidationResult(failures.isEmpty(), failures);
    }

    public ValidationResult validateDelete(Long id) {
        final UserDto dto = new UserDto();
        dto.setId(id);

        List<ValidationFailure> failures = new ArrayList<>();
        validateId(dto, failures);

        return new ValidationResult(failures.isEmpty(), failures);
    }

    private void validateCommon(UserDto userDto, List<ValidationFailure> failures) {
        validatePassword(userDto, failures);
        validateUserName(userDto, failures);
        validateFirstName(userDto, failures);
        validateLastName(userDto, failures);
        validateEmail(userDto, failures);
        validateCountry(userDto, failures);
    }

    private void validateNullId(UserDto userDto, List<ValidationFailure> failures) {
        ValidationFailuresBuilder.create("id", userDto.getId())
                .validate(id -> id == null, "id has to be null for resource creation")
                .build().stream().forEach(failures::add);
    }

    private void validateId(UserDto userDto, List<ValidationFailure> failures) {
        ValidationFailuresBuilder.create("id", userDto.getId())
                .validate(id -> id != null, "id cannot be null")
                .validate(id -> id > 0L, "id has to be greater than 0")
                .build().stream().forEach(failures::add);
    }

    private void validateCountry(UserDto userDto, List<ValidationFailure> failures) {
        ValidationFailuresBuilder.create("country", userDto.getCountry())
                .validate(un -> un != null, "country cannot be null")
                .validate(un -> !un.isEmpty(), "country cannot be empty")
                .validate(un -> un.length() > 2 && un.length() < 256,
                        "country has to have minimum of 2 characters and a maximum of 64")
                .build().stream().forEach(failures::add);
    }

    private void validateEmail(UserDto userDto, List<ValidationFailure> failures) {
        ValidationFailuresBuilder.create("email", userDto.getEmail())
                .validate(un -> un != null, "email cannot be null")
                .validate(un -> !un.isEmpty(), "email cannot be empty")
                .validate(un -> un.length() > 2 && un.length() < 256,
                        "email has to have minimum of 2 characters and a maximum of 64")
                .build().stream().forEach(failures::add);
    }

    private void validateLastName(UserDto userDto, List<ValidationFailure> failures) {
        ValidationFailuresBuilder.create("lastName", userDto.getLastName())
                .validate(un -> un != null, "last name cannot be null")
                .validate(un -> !un.isEmpty(), "last name cannot be empty")
                .validate(un -> un.length() > 2 && un.length() < 256,
                        "last name has to have minimum of 2 characters and a maximum of 64")
                .build().stream().forEach(failures::add);
    }

    private void validateFirstName(UserDto userDto, List<ValidationFailure> failures) {
        ValidationFailuresBuilder.create("firstName", userDto.getFirstName())
                .validate(un -> un != null, "first name cannot be null")
                .validate(un -> !un.isEmpty(), "first name cannot be empty")
                .validate(un -> un.length() > 2 && un.length() < 128,
                        "first name has to have minimum of 2 characters and a maximum of 64")
                .build().stream().forEach(failures::add);
    }

    private void validateUserName(UserDto userDto, List<ValidationFailure> failures) {
        ValidationFailuresBuilder.create("userName", userDto.getUserName())
                .validate(un -> un != null, "username cannot be null")
                .validate(un -> !un.isEmpty(), "username cannot be empty")
                .validate(un -> un.length() > 2 && un.length() < 64,
                        "username has to have minimum of 2 characters and a maximum of 64")
                .build().stream().forEach(failures::add);
    }

    private void validatePassword(UserDto userDto, List<ValidationFailure> failures) {
        ValidationFailuresBuilder.create("password", userDto.getPassword())
                .validate(un -> un != null, "password cannot be null")
                .validate(un -> !un.isEmpty(), "password cannot be empty")
                .validate(un -> un.length() > 2 && un.length() < 64,
                        "password has to have minimum of 2 characters and a maximum of 64")
                .build().stream().forEach(failures::add);
    }

}
