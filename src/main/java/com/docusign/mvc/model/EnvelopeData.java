
package com.docusign.mvc.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnvelopeData {

    private List<EnvelopeDataList> envelopeDataList = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public List<EnvelopeDataList> getEnvelopeDataList() {
        return envelopeDataList;
    }

    public void setEnvelopeDataList(List<EnvelopeDataList> envelopeDataList) {
        this.envelopeDataList = envelopeDataList;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
