
package com.docusign.mvc.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "reminders",
    "expirations"
})
public class Notification {

	@JsonProperty("expirations")
    private Expirations expirations;
    
    @JsonProperty("reminders")
    private Reminders reminders;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("expirations")
    public Expirations getExpirations() {
        return expirations;
    }

    @JsonProperty("expirations")
    public void setExpirations(Expirations expirations) {
        this.expirations = expirations;
    }

    @JsonProperty("reminders")
    public Reminders getReminders() {
        return reminders;
    }

    @JsonProperty("reminders")
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
