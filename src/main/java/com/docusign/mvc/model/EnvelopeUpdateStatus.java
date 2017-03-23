
package com.docusign.mvc.model;

import java.util.HashMap;
import java.util.Map;

public class EnvelopeUpdateStatus {

    private String batchId;
    private String status;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
