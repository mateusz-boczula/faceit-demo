package pl.boczula.mateusz.faceitdemo.controller;

import org.springframework.web.bind.annotation.*;
import pl.boczula.mateusz.faceitdemo.dto.UserDto;
import pl.boczula.mateusz.faceitdemo.dto.WebhookDto;
import pl.boczula.mateusz.faceitdemo.enums.RestActionType;
import pl.boczula.mateusz.faceitdemo.mapper.WebhookMapper;
import pl.boczula.mateusz.faceitdemo.service.WebhookService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/webhooks")
public class WebhookController {

    private final WebhookService service;
    private final WebhookMapper mapper;

    public WebhookController(WebhookService service, WebhookMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/")
    public Flux<WebhookDto> findWebhooks(@RequestParam Map<String, String> requestParams) {
        return service.findAll().map(mapper::toWebhookDto);
    }

    @GetMapping("/{id}")
    public Mono<WebhookDto> findWebhook(@PathVariable Long id) {
        return service.findWebhook(id).map(mapper::toWebhookDto);
    }

    @PostMapping("/")
    public Mono<WebhookDto> createWebhook(@RequestBody WebhookDto dto) {
        return service.createOrUpdateWebhook(mapper.toWebhook(dto)).map(mapper::toWebhookDto);
    }

    @PutMapping("/{id}")
    public Mono<WebhookDto> updateWebhook(@PathVariable Long id, @RequestBody WebhookDto dto) {
        dto.setId(id);
        return service.createOrUpdateWebhook(mapper.toWebhook(dto)).map(mapper::toWebhookDto);
    }

    @DeleteMapping("/{id}")
    public Mono<WebhookDto> deleteWebhook(@PathVariable Long id) {
        return service.deleteWebhook(id).map(mapper::toWebhookDto);
    }

}
