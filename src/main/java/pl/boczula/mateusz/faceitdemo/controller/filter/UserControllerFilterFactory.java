package pl.boczula.mateusz.faceitdemo.controller.filter;

import org.springframework.stereotype.Component;
import pl.boczula.mateusz.faceitdemo.dto.UserDto;

import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

@Component
public class UserControllerFilterFactory {

    public Predicate<UserDto> createUserFilter(Map<String, String> requestParams) {
        return requestParams.entrySet().stream()
                .map(UserControllerFilterFactory::getFilter)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .reduce(u -> true, (u1, u2) -> u1.and(u2));

    }

    private static Optional<Predicate<UserDto>> getFilter(Map.Entry<String, String> requestParam) {
        switch(requestParam.getKey()) {
            case "userName":    return Optional.of(u -> requestParam.getValue().equals(u.getUserName()));
            case "country":     return Optional.of(u -> requestParam.getValue().equals(u.getCountry()));
            case "firstName":   return Optional.of(u -> requestParam.getValue().equals(u.getFirstName()));
            case "lastName":    return Optional.of(u -> requestParam.getValue().equals(u.getLastName()));

            default: return Optional.empty();
        }
    }

}
