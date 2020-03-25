package pl.boczula.mateusz.faceitdemo.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import pl.boczula.mateusz.faceitdemo.controller.filter.UserControllerFilterFactory;
import pl.boczula.mateusz.faceitdemo.controller.validator.UserValidator;
import pl.boczula.mateusz.faceitdemo.dto.UserDto;
import pl.boczula.mateusz.faceitdemo.mapper.UserMapper;
import pl.boczula.mateusz.faceitdemo.repository.UserRepository;
import pl.boczula.mateusz.faceitdemo.service.UserService;
import pl.boczula.mateusz.faceitdemo.service.WebhookService;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = UserController.class)
@Import({UserController.class, UserService.class, UserRepository.class, UserMapper.class, UserValidator.class, UserControllerFilterFactory.class})
public class UserControllerTest {

    public static final String UPDATED_USER_NAME = "updated";
    @MockBean
    private WebhookService webhookService;

    @Autowired
    private WebTestClient client;

    @Test
    public void when_invalidUserData_shouldNot_createUser() {
        final UserDto userDto = getSampleUser();
        userDto.setUserName("");

        client.post()
                .uri("/users/")
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(userDto))
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    public void when_validUserResourceSupplied_should_createUser() {
        final UserDto userDto = getSampleUser();

        client.post()
                .uri("/users/")
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(userDto))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(UserDto.class)
                .consumeWith(u -> {
                    client.get()
                            .uri("/users/" + u.getResponseBody().getId())
                            .exchange()
                            .expectStatus().is2xxSuccessful();
                });
    }

    @Test
    public void when_userNotFound_shouldNot_updateUser() {
        final UserDto userDto = getSampleUser();
        userDto.setId(999L);

        client.put()
                .uri("/users/" + userDto.getId())
                .body(BodyInserters.fromValue(userDto))
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    public void when_userDataInvalid_shouldNot_updateUser() {
        final UserDto userDto = getSampleUser();
        userDto.setUserName("");

        client.put()
                .uri("/users/" + userDto.getId())
                .body(BodyInserters.fromValue(userDto))
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    public void when_validUserResourceSupplied_should_updateUser() {
        final UserDto userDto = getSampleUser();

        client.post()
                .uri("/users/")
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(userDto))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(UserDto.class)
                .consumeWith(u -> {
                    u.getResponseBody().setUserName(UPDATED_USER_NAME);
                    client.put()
                            .uri("/users/" + u.getResponseBody().getId())
                            .body(BodyInserters.fromValue(u.getResponseBody()))
                            .exchange()
                            .expectStatus().is2xxSuccessful()
                            .expectBody(UserDto.class)
                            .consumeWith(er -> assertEquals(UPDATED_USER_NAME, er.getResponseBody().getUserName()));
                });
    }

    @Test
    public void when_userIdNegative_shouldNot_deleteUser() {
        client.delete()
                .uri("/users/" + -1)
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    public void when_userIdNotFound_shouldNot_deleteUser() {
        client.delete()
                .uri("/users/" + 999)
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    public void when_validUserIdSupplied_should_deleteUser() {
        final UserDto userDto = getSampleUser();

        client.post()
                .uri("/users/")
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(userDto))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(UserDto.class)
                .consumeWith(u -> {
                    u.getResponseBody().setUserName("updated");
                    client.delete()
                            .uri("/users/" + u.getResponseBody().getId())
                            .exchange()
                            .expectStatus().is2xxSuccessful();

                    client.get()
                            .uri("/users/" + u.getResponseBody().getId())
                            .exchange()
                            .expectStatus().is2xxSuccessful()
                            .expectBody(UserDto.class).isEqualTo(null);
                });

    }

    private UserDto getSampleUser() {
        final UserDto userDto = new UserDto();
        userDto.setUserName("kareem");
        userDto.setFirstName("Mateusz");
        userDto.setLastName("Boczula");
        userDto.setPassword("asdf123");
        userDto.setEmail("asdf@asdf.pl");
        userDto.setCountry("Poland");
        return userDto;
    }

}
