package pl.boczula.mateusz.faceitdemo.service;

import org.springframework.stereotype.Service;
import pl.boczula.mateusz.faceitdemo.repository.UserRepository;
import pl.boczula.mateusz.faceitdemo.entity.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Flux<User> findAll() {
        return repository.findAll();
    }

    public Mono<User> findUser(Long id) {
        return repository.findUser(id);
    }

    public Mono<User> createOrUpdateUser(User user) {
        return repository.saveUser(user);
    }

    public Mono<User> deleteUser(Long id) {
        return repository.deleteUser(id);
    }

}
