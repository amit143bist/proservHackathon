package com.docusign.envelopes.batch.file.mapper;

import java.math.BigInteger;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.docusign.envelopes.batch.file.domain.EnvelopeDetails;
import com.docusign.envelopes.exception.EmptyDataException;


/**
 * @author Amit.Bist
 *
 */
public class EnvelopeSetMapper implements FieldSetMapper<EnvelopeDetails> {

	private String reminderEnabled;
	
	private String expirationEnabled;
	
	public String getReminderEnabled() {
		return reminderEnabled;
	}

	public void setReminderEnabled(String reminderEnabled) {
		this.reminderEnabled = reminderEnabled;
	}

	public String getExpirationEnabled() {
		return expirationEnabled;
	}

	public void setExpirationEnabled(String expirationEnabled) {
		this.expirationEnabled = expirationEnabled;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.batch.item.file.mapping.FieldSetMapper#mapFieldSet(
	 * org.springframework.batch.item.file.transform.FieldSet)
	 * 
	 * envelopeId,reminderEnabled,reminderDelay,reminderFrequency,expireEnabled,
	 * expireAfter,expireWarn
	 */
	@Override
	public EnvelopeDetails mapFieldSet(FieldSet fieldSet) throws BindException {
		
		System.out.println("EnvelopeSetMapper.mapFieldSet()- " + fieldSet);

		EnvelopeDetails envelopeDetails = new EnvelopeDetails();

		if (isValueNotEmpty(fieldSet.readString(0))) {

			envelopeDetails.setEnvelopeId(fieldSet.readString(0));
		} else {
			throw new EmptyDataException("envelopeId");
		}

		if ("true".equalsIgnoreCase(reminderEnabled)) {

			if (isValueNotEmpty(fieldSet.readRawString(1))) {
				envelopeDetails.setReminderEnabled(fieldSet.readBoolean(1));
			} else {
				throw new EmptyDataException("reminderEnabled");
			}

			if (isValueNotEmpty(fieldSet.readRawString(2))) {
				envelopeDetails.setReminderDelay(BigInteger.valueOf(fieldSet.readInt(2)));
			} else {
				throw new EmptyDataException("reminderDelay");
			}

			if (isValueNotEmpty(fieldSet.readRawString(3))) {
				envelopeDetails.setReminderFrequency(BigInteger.valueOf(fieldSet.readInt(3)));
			} else {
				throw new EmptyDataException("reminderFrequency");
			}
		}

		if ("true".equalsIgnoreCase(expirationEnabled)) {

			if (isValueNotEmpty(fieldSet.readRawString(4))) {
				envelopeDetails.setExpireEnabled(fieldSet.readBoolean(4));
			} else {
				throw new EmptyDataException("expireEnabled");
			}

			if (isValueNotEmpty(fieldSet.readRawString(5))) {
				envelopeDetails.setExpireAfter(BigInteger.valueOf(fieldSet.readInt(5)));
			} else {
				throw new EmptyDataException("expireAfter");
			}

			if (isValueNotEmpty(fieldSet.readRawString(6))) {
				envelopeDetails.setExpireWarn(BigInteger.valueOf(fieldSet.readInt(6)));
			} else {
				throw new EmptyDataException("expireWarn");
			}
		}

			System.out.println("EnvelopeSetMapper.mapFieldSet()- " + envelopeDetails.getEnvelopeId());
		return envelopeDetails;
	}

	/**
	 * @param value
	 */
	private boolean isValueNotEmpty(Object value) {

		boolean valueNotEmpty = false;

		if (null != value && !"".equalsIgnoreCase((String) value)) {
			valueNotEmpty = true;
		}

		return valueNotEmpty;
	}

}