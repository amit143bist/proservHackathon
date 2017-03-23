
package com.docusign.mvc.model;

import java.util.HashMap;
import java.util.Map;

public class Expirations {

    private String expireAfter;
    private String expireEnabled;
    private String expireWarn;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getExpireAfter() {
        return expireAfter;
    }

    public void setExpireAfter(String expireAfter) {
        this.expireAfter = expireAfter;
    }

    public String getExpireEnabled() {
        return expireEnabled;
    }

    public void setExpireEnabled(String expireEnabled) {
        this.expireEnabled = expireEnabled;
    }

    public String getExpireWarn() {
        return expireWarn;
    }

    public void setExpireWarn(String expireWarn) {
        this.expireWarn = expireWarn;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
