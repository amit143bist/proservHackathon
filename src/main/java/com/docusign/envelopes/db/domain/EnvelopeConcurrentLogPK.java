/**
 * 
 */
package com.docusign.envelopes.db.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;

/**
 * @author Amit.Bist
 *
 */
@Embeddable
public class EnvelopeConcurrentLogPK {

	@Id
	@Column(name = "EnvelopeJobId")
	private String envelopeJobId;
	
	@Column(name = "EnvelopeId")
	private String envelopeId;

	public String getEnvelopeJobId() {
		return envelopeJobId;
	}

	public void setEnvelopeJobId(String envelopeJobId) {
		this.envelopeJobId = envelopeJobId;
	}

	public String getEnvelopeId() {
		return envelopeId;
	}

	public void setEnvelopeId(String envelopeId) {
		this.envelopeId = envelopeId;
	}

}