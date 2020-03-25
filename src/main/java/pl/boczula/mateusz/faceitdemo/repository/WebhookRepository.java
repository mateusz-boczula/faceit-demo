package pl.boczula.mateusz.faceitdemo.repository;

import org.springframework.stereotype.Component;
import pl.boczula.mateusz.faceitdemo.entity.Webhook;
import pl.boczula.mateusz.faceitdemo.enums.RestActionType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class WebhookRepository {

    private static AtomicLong ID_GENERATOR = new AtomicLong(1);
    private Map<Long, Webhook> webhooks = new ConcurrentHashMap<>();

    public Flux<Webhook> findAll() {
        return Flux.fromIterable(webhooks.values());
    }

    public Flux<Webhook> findByActionTypeAndEntityType(RestActionType actionType, String entityType) {
        return findAll().filter(w -> actionType == w.getActionType() && entityType.equals(w.getEntityType()));
    }

    public Mono<Webhook> findWebhook(Long id) {
        return Mono.justOrEmpty(webhooks.getOrDefault(id, null));
    }

    public Mono<Webhook> saveWebhook(Webhook webhook) {
        if(webhook.getId() == null) {
            webhook.setId(ID_GENERATOR.getAndIncrement());
        }

        return Mono.just(webhooks.merge(webhook.getId(), webhook, (w1, w2) -> w2));
    }

    public Mono<Webhook> deleteWebhook(Long id) {
        return Mono.justOrEmpty(webhooks.remove(id));
    }

}
