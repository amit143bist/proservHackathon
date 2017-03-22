/**
 * 
 */
package com.docusign.envelopes.db.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Amit.Bist
 *
 */
@Entity
@Table(name = "EnvelopeScheduledTask")
public class EnvelopeScheduledTask {

	@Id
	@Column(name = "EnvelopeJobId")
	private String envelopeJobId;
	
	@Column(name = "EnvelopeJobTypeId")
	private String envelopeJobTypeId;
	
	@Column(name = "EnvelopeJobAccountId")
	private String envelopeJobAccountId;
	
	@Column(name = "EnvelopeJobStartDateTime")
	private Timestamp envelopeJobStartDateTime;
	
	@Column(name = "EnvelopeJobEndDateTime")
	private Timestamp envelopeJobEndDateTime;
	
	@Column(name = "EnvelopeJobCreateDateTime")
	private Timestamp envelopeJobCreateDateTime;
	
	@Column(name = "EnvelopeJobLastModifiedDateTime")
	private Timestamp envelopeJobLastModifiedDateTime;

	public String getEnvelopeJobId() {
		return envelopeJobId;
	}

	public void setEnvelopeJobId(String envelopeJobId) {
		this.envelopeJobId = envelopeJobId;
	}

	public String getEnvelopeJobTypeId() {
		return envelopeJobTypeId;
	}

	public void setEnvelopeJobTypeId(String envelopeJobTypeId) {
		this.envelopeJobTypeId = envelopeJobTypeId;
	}

	public String getEnvelopeJobAccountId() {
		return envelopeJobAccountId;
	}

	public void setEnvelopeJobAccountId(String envelopeJobAccountId) {
		this.envelopeJobAccountId = envelopeJobAccountId;
	}

	public Timestamp getEnvelopeJobStartDateTime() {
		return envelopeJobStartDateTime;
	}

	public void setEnvelopeJobStartDateTime(Timestamp envelopeJobStartDateTime) {
		this.envelopeJobStartDateTime = envelopeJobStartDateTime;
	}

	public Timestamp getEnvelopeJobEndDateTime() {
		return envelopeJobEndDateTime;
	}

	public void setEnvelopeJobEndDateTime(Timestamp envelopeJobEndDateTime) {
		this.envelopeJobEndDateTime = envelopeJobEndDateTime;
	}

	public Timestamp getEnvelopeJobCreateDateTime() {
		return envelopeJobCreateDateTime;
	}

	public void setEnvelopeJobCreateDateTime(Timestamp envelopeJobCreateDateTime) {
		this.envelopeJobCreateDateTime = envelopeJobCreateDateTime;
	}

	public Timestamp getEnvelopeJobLastModifiedDateTime() {
		return envelopeJobLastModifiedDateTime;
	}

	public void setEnvelopeJobLastModifiedDateTime(Timestamp envelopeJobLastModifiedDateTime) {
		this.envelopeJobLastModifiedDateTime = envelopeJobLastModifiedDateTime;
	}
}
