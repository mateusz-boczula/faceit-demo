package pl.boczula.mateusz.faceitdemo.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import pl.boczula.mateusz.faceitdemo.enums.RestActionType;

public class RestActionDto<T extends Dto> {

    private RestActionType actionType;
    private String entityType;
    private T dto;

    public RestActionDto() {
    }

    public RestActionDto(RestActionType actionType, String entityType, T dto) {
        this.actionType = actionType;
        this.entityType = entityType;
        this.dto = dto;
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

    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
            property = "entityType",
            defaultImpl = Void.class)
    @JsonSubTypes({
            @JsonSubTypes.Type(value = UserDto.class, name = "User"),
            @JsonSubTypes.Type(value = WebhookDto.class, name = "Webhook")
    })
    public T getDto() {
        return dto;
    }

    public void setDto(T dto) {
        this.dto = dto;
    }

    @Override
    public String toString() {
        return "RestActionDto{" +
                "actionType=" + actionType +
                ", entityType='" + entityType + '\'' +
                ", dto=" + dto +
                '}';
    }
}
