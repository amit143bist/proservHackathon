
package com.docusign.mvc.model;

import java.util.HashMap;
import java.util.Map;

public class Reminders {

    private String reminderDelay;
    private String reminderEnabled;
    private String reminderFrequency;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getReminderDelay() {
        return reminderDelay;
    }

    public void setReminderDelay(String reminderDelay) {
        this.reminderDelay = reminderDelay;
    }

    public String getReminderEnabled() {
        return reminderEnabled;
    }

    public void setReminderEnabled(String reminderEnabled) {
        this.reminderEnabled = reminderEnabled;
    }

    public String getReminderFrequency() {
        return reminderFrequency;
    }

    public void setReminderFrequency(String reminderFrequency) {
        this.reminderFrequency = reminderFrequency;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
