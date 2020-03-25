package pl.boczula.mateusz.faceitdemo.dto;

import pl.boczula.mateusz.faceitdemo.dto.validation.ValidationFailure;
import pl.boczula.mateusz.faceitdemo.dto.validation.ValidationResult;

import java.util.Collections;
import java.util.Objects;

public class UserDto implements Dto {

    private Long id;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String country;

    @Override
    public ValidationResult getValidationResult() {
        return validationResult;
    }

    public void setValidationResult(ValidationResult validationResult) {
        this.validationResult = validationResult;
    }

    private ValidationResult validationResult;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return id.equals(userDto.id) &&
                userName.equals(userDto.userName) &&
                password.equals(userDto.password) &&
                firstName.equals(userDto.firstName) &&
                lastName.equals(userDto.lastName) &&
                email.equals(userDto.email) &&
                country.equals(userDto.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, password, firstName, lastName, email, country);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    public static UserDto notFound(Long id) {
        final UserDto dto = new UserDto();
        dto.setId(id);
        dto.setValidationResult(
                new ValidationResult(false,
                        Collections.singletonList(new ValidationFailure("id",
                                "Could not find user with id " + id))));
        return dto;
    }
}