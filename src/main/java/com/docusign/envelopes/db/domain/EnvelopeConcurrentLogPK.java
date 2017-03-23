/**
 * 
 */
package com.docusign.envelopes.db.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Amit.Bist
 *
 */
@Embeddable
public class EnvelopeConcurrentLogPK implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5512880776911935408L;

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