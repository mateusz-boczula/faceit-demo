package pl.boczula.mateusz.faceitdemo.mapper;

import org.springframework.stereotype.Component;
import pl.boczula.mateusz.faceitdemo.dto.WebhookDto;
import pl.boczula.mateusz.faceitdemo.dto.validation.ValidationResult;
import pl.boczula.mateusz.faceitdemo.entity.Webhook;

@Component
public class WebhookMapper {

    public Webhook toWebhook(WebhookDto dto) {
        final Webhook webhook = new Webhook();
        webhook.setId(dto.getId());
        webhook.setActionType(dto.getActionType());
        webhook.setEntityType(dto.getEntityType());
        webhook.setPostUrl(dto.getPostUrl());
        return webhook;
    }

    public WebhookDto toWebhookDto(Webhook entity) {
        final WebhookDto webhookDto = new WebhookDto();
        webhookDto.setId(entity.getId());
        webhookDto.setActionType(entity.getActionType());
        webhookDto.setEntityType(entity.getEntityType());
        webhookDto.setPostUrl(entity.getPostUrl());
        webhookDto.setValidationResult(ValidationResult.ok());
        return webhookDto;
    }
}
