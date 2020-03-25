package pl.boczula.mateusz.faceitdemo.repository;

import org.springframework.stereotype.Component;
import pl.boczula.mateusz.faceitdemo.entity.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class UserRepository {
    private static AtomicLong ID_GENERATOR = new AtomicLong(1);

    private Map<Long, User> users = new ConcurrentHashMap<>();

    public Flux<User> findAll() {
        return Flux.fromIterable(users.values());
    }

    public Mono<User> findUser(Long id) {
        return Mono.justOrEmpty(users.getOrDefault(id, null));
    }

    public Mono<User> saveUser(User user) {
        if(user.getId() == null) {
            user.setId(ID_GENERATOR.getAndIncrement());
        }

        return Mono.just(users.merge(user.getId(), user, (u1, u2) -> u2));
    }

    public Mono<User> deleteUser(Long id) {
        return Mono.just(id)
                .map(i -> {
                    if(users.get(i) == null) {
                        throw new IllegalArgumentException("User to delete not found");
                    }
                    return users.get(i);
                })
                .map(u -> users.remove(u.getId()));
    }
}
