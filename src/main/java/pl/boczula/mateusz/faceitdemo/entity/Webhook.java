package pl.boczula.mateusz.faceitdemo.entity;

import pl.boczula.mateusz.faceitdemo.enums.RestActionType;

public class Webhook {

    private Long id;
    private RestActionType actionType;
    private String entityType;
    private String postUrl;

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
}
