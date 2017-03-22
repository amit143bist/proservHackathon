/**
 * 
 */
package com.docusign.envelopes.db.domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Amit.Bist
 *
 */
@Entity
@Table(name = "EnvelopeConcurrentLog")
public class EnvelopeConcurrentLog {


	@EmbeddedId
	private EnvelopeConcurrentLogPK envelopeConcurrentLogPK;
	
	@Column(name = "envelopeParams")
	private String envelopeParams;
	
	@Column(name = "EnvelopeSuccessFlag")
	private boolean envelopeSuccessFlag;
	
	@Column(name = "EnvelopeTransactionComments")
	private String envelopeTransactionComments;

	public EnvelopeConcurrentLogPK getEnvelopeConcurrentLogPK() {
		return envelopeConcurrentLogPK;
	}

	public void setEnvelopeConcurrentLogPK(EnvelopeConcurrentLogPK envelopeConcurrentLogPK) {
		this.envelopeConcurrentLogPK = envelopeConcurrentLogPK;
	}

	public String getEnvelopeParams() {
		return envelopeParams;
	}

	public void setEnvelopeParams(String envelopeParams) {
		this.envelopeParams = envelopeParams;
	}

	public boolean isEnvelopeSuccessFlag() {
		return envelopeSuccessFlag;
	}

	public void setEnvelopeSuccessFlag(boolean envelopeSuccessFlag) {
		this.envelopeSuccessFlag = envelopeSuccessFlag;
	}

	public String getEnvelopeTransactionComments() {
		return envelopeTransactionComments;
	}

	public void setEnvelopeTransactionComments(String envelopeTransactionComments) {
		this.envelopeTransactionComments = envelopeTransactionComments;
	}

}
