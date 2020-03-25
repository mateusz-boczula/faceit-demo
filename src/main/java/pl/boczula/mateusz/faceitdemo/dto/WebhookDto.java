package pl.boczula.mateusz.faceitdemo.dto;

import pl.boczula.mateusz.faceitdemo.dto.validation.ValidationResult;
import pl.boczula.mateusz.faceitdemo.enums.RestActionType;

import java.util.Objects;

public class WebhookDto implements Dto {

    private Long id;
    private RestActionType actionType;
    private String entityType;
    private String postUrl;

    @Override
    public ValidationResult getValidationResult() {
        return validationResult;
    }

    public void setValidationResult(ValidationResult validationResult) {
        this.validationResult = validationResult;
    }

    private ValidationResult validationResult;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RestActionType getActionType() {
        return actionType;
    }

    public void setActionType(RestActionType actionType) {
        this.actionType = actionType;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebhookDto that = (WebhookDto) o;
        return id.equals(that.id) &&
                actionType == that.actionType &&
                entityType.equals(that.entityType) &&
                postUrl.equals(that.postUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, actionType, entityType, postUrl);
    }

    @Override
    public String toString() {
        return "WebhookDto{" +
                "id=" + id +
                ", actionType=" + actionType +
                ", entityType='" + entityType + '\'' +
                ", postUrl='" + postUrl + '\'' +
                '}';
    }
}
