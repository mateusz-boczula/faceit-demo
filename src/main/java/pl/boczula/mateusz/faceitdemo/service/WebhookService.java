package pl.boczula.mateusz.faceitdemo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import pl.boczula.mateusz.faceitdemo.dto.Dto;
import pl.boczula.mateusz.faceitdemo.dto.RestActionDto;
import pl.boczula.mateusz.faceitdemo.entity.Webhook;
import pl.boczula.mateusz.faceitdemo.enums.RestActionType;
import pl.boczula.mateusz.faceitdemo.repository.WebhookRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class WebhookService {

    private final WebhookRepository repository;

    public WebhookService(WebhookRepository repository) {
        this.repository = repository;
    }

    public <T extends Dto> void callWebhookEndpoints(RestActionType actionType, String entityType, T dto) {
        repository.findByActionTypeAndEntityType(actionType, entityType)
                .subscribe(w -> WebClient.create()
                        .post().uri(w.getPostUrl())
                        .body(BodyInserters.fromValue(new RestActionDto(RestActionType.CREATE, "User", dto)))
                        .exchange()
                        .subscribe());
    }

    public Flux<Webhook> findAll() {
        return repository.findAll();
    }

    public Mono<Webhook> findWebhook(Long id) {
        return repository.findWebhook(id);
    }

    public Mono<Webhook> createOrUpdateWebhook(Webhook webhook) {
        return repository.saveWebhook(webhook);
    }

    public Mono<Webhook> deleteWebhook(Long id) {
        return repository.deleteWebhook(id);
    }
}
