package com.docusign.envelope.file.mapper;

import java.math.BigInteger;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.docusign.envelope.exception.EmptyDataException;
import com.docusign.envelope.file.domain.EnvelopeDetails;


/**
 * @author Amit.Bist
 *
 */
public class EnvelopeSetMapper implements FieldSetMapper<EnvelopeDetails> {

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

		EnvelopeDetails envelopeDetails = new EnvelopeDetails();

		if (isValueNotEmpty(fieldSet.readString(0))) {

			envelopeDetails.setEnvelopeId(fieldSet.readString(0));
		} else {
			throw new EmptyDataException("envelopeId");
		}

//		if (null != appParameters && null != appParameters.getReminderAllowed() && appParameters.getReminderAllowed()) {

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
//		}

		/*if (null != appParameters && null != appParameters.getExpirationAllowed()
				&& appParameters.getExpirationAllowed()) {*/

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
//		}

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