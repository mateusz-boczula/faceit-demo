package pl.boczula.mateusz.faceitdemo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.boczula.mateusz.faceitdemo.controller.filter.UserControllerFilterFactory;
import pl.boczula.mateusz.faceitdemo.controller.validator.UserValidator;
import pl.boczula.mateusz.faceitdemo.dto.UserDto;
import pl.boczula.mateusz.faceitdemo.dto.validation.ValidationResult;
import pl.boczula.mateusz.faceitdemo.enums.RestActionType;
import pl.boczula.mateusz.faceitdemo.mapper.UserMapper;
import pl.boczula.mateusz.faceitdemo.service.UserService;
import pl.boczula.mateusz.faceitdemo.service.WebhookService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService service;
    private final WebhookService webhookService;
    private final UserMapper mapper;
    private final UserControllerFilterFactory filterFactory;
    private final UserValidator validator;

    public UserController(UserService service, WebhookService webhookService, UserMapper mapper, UserControllerFilterFactory filterFactory, UserValidator validator) {
        this.service = service;
        this.webhookService = webhookService;
        this.mapper = mapper;
        this.filterFactory = filterFactory;
        this.validator = validator;
    }

    @GetMapping("/")
    public Flux<UserDto> findUsers(@RequestParam Map<String, String> requestParams) {
        return service.findAll().map(mapper::toUserDto).filter(filterFactory.createUserFilter(requestParams));
    }

    @GetMapping("/{id}")
    public Mono<UserDto> findUser(@PathVariable Long id) {
        return service.findUser(id).map(mapper::toUserDto);
    }

    @PostMapping("/")
    public Mono<ResponseEntity<UserDto>> createUser(@RequestBody UserDto dto) {
        final ValidationResult validationResult = validator.validateCreate(dto);
        dto.setValidationResult(validationResult);
        if (validationResult.isSuccess()) {
            return service.createOrUpdateUser(mapper.toUser(dto))
                    .map(mapper::toUserDto)
                    .doOnSuccess(u -> webhookService.callWebhookEndpoints(RestActionType.CREATE, "User", u))
                    .map(ResponseEntity::ok);
        }
        else {
            LOGGER.info("validation for user creation failed: {}", validationResult);
            return Mono.just(ResponseEntity.badRequest().body(dto));
        }
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<UserDto>> updateUser(@PathVariable Long id, @RequestBody UserDto dto) {
        dto.setId(id);
        final ValidationResult validationResult = validator.validateUpdate(dto);
        dto.setValidationResult(validationResult);
        if(validationResult.isSuccess()) {
            return service.findUser(id)
                    .flatMap(u -> service.createOrUpdateUser(mapper.toUser(dto)))
                    .map(mapper::toUserDto)
                    .doOnSuccess(u -> webhookService.callWebhookEndpoints(RestActionType.UPDATE, "User", u))
                    .map(ResponseEntity::ok)
                    .switchIfEmpty(
                            Mono.just(ResponseEntity.badRequest().body(UserDto.notFound(id))));
        }
        else {
            LOGGER.info("validation for user modification failed: {}", validationResult);
            return Mono.just(ResponseEntity.badRequest().body(dto));
        }
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<UserDto>> deleteUser(@PathVariable Long id) {
        final ValidationResult validationResult = validator.validateDelete(id);

        if(validationResult.isSuccess()) {
            return service.findUser(id)
                    .flatMap(u -> service.deleteUser(u.getId()))
                    .map(mapper::toUserDto)
                    .doOnSuccess(u -> webhookService.callWebhookEndpoints(RestActionType.DELETE, "User", u))
                    .map(ResponseEntity::ok)
                    .switchIfEmpty(
                            Mono.just(ResponseEntity.badRequest().body(UserDto.notFound(id))));
        }
        else {
            LOGGER.info("validation for user deletion failed: {}", validationResult);
            final UserDto dto = new UserDto();
            dto.setId(id);
            return Mono.just(ResponseEntity.badRequest().body(dto));
        }
    }

}
