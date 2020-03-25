package pl.boczula.mateusz.faceitdemo.mapper;

import org.springframework.stereotype.Component;
import pl.boczula.mateusz.faceitdemo.dto.UserDto;
import pl.boczula.mateusz.faceitdemo.dto.validation.ValidationResult;
import pl.boczula.mateusz.faceitdemo.entity.User;

@Component
public class UserMapper {

    public User toUser(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setUserName(dto.getUserName());
        user.setPassword(dto.getPassword());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setCountry(dto.getCountry());

        return user;
    }

    public UserDto toUserDto(User entity) {
        UserDto userDto = new UserDto();
        userDto.setId(entity.getId());
        userDto.setUserName(entity.getUserName());
        userDto.setPassword(entity.getPassword());
        userDto.setFirstName(entity.getFirstName());
        userDto.setLastName(entity.getLastName());
        userDto.setEmail(entity.getEmail());
        userDto.setCountry(entity.getCountry());
        userDto.setValidationResult(ValidationResult.ok());

        return userDto;
    }

}
