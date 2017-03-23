
package com.docusign.mvc.model;

import java.util.HashMap;
import java.util.Map;

public class Notification {

    private Expirations expirations;
    private Reminders reminders;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Expirations getExpirations() {
        return expirations;
    }

    public void setExpirations(Expirations expirations) {
        this.expirations = expirations;
    }

    public Reminders getReminders() {
        return reminders;
    }

    public void setReminders(Reminders reminders) {
        this.reminders = reminders;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
