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
@Table(name = "EnvelopeJobType")
public class EnvelopeJobType {


	@Id
	@Column(name = "EnvelopeJobTypeId")
	private String envelopeJobTypeId;
	
	@Column(name = "EnvelopeJobTypeDesc")
	private String envelopeJobTypeDesc;
	
	@Column(name = "EnvelopeJobTypeCreateDateTime")
	private Timestamp envelopeJobTypeCreateDateTime;

	public String getEnvelopeJobTypeId() {
		return envelopeJobTypeId;
	}

	public void setEnvelopeJobTypeId(String envelopeJobTypeId) {
		this.envelopeJobTypeId = envelopeJobTypeId;
	}

	public String getEnvelopeJobTypeDesc() {
		return envelopeJobTypeDesc;
	}

	public void setEnvelopeJobTypeDesc(String envelopeJobTypeDesc) {
		this.envelopeJobTypeDesc = envelopeJobTypeDesc;
	}

	public Timestamp getEnvelopeJobTypeCreateDateTime() {
		return envelopeJobTypeCreateDateTime;
	}

	public void setEnvelopeJobTypeCreateDateTime(Timestamp envelopeJobTypeCreateDateTime) {
		this.envelopeJobTypeCreateDateTime = envelopeJobTypeCreateDateTime;
	}
}
