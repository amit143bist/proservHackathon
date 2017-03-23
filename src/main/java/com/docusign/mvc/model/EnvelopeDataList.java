
package com.docusign.mvc.model;

import java.util.HashMap;
import java.util.Map;

public class EnvelopeDataList {

    private String envelopeId;
    private Notification notification;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getEnvelopeId() {
        return envelopeId;
    }

    public void setEnvelopeId(String envelopeId) {
        this.envelopeId = envelopeId;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
