/**
 * 
 */
package com.docusign.envelopes.batch.file.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.docusign.envelopes.batch.file.domain.EnvelopeDetails;
import com.docusign.envelopes.dao.EnvelopesDocuServiceDAO;
import com.docusign.envelopes.ds.domain.DSErrors;
import com.docusign.envelopes.ds.domain.Expirations;
import com.docusign.envelopes.ds.domain.Notification;
import com.docusign.envelopes.ds.domain.Reminders;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * @author Amit.Bist
 *
 */
public class DSNotificationProcessor implements ItemProcessor<EnvelopeDetails, EnvelopeDetails> {

	private static final Logger logger = LoggerFactory.getLogger(DSNotificationProcessor.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	EnvelopesDocuServiceDAO envelopesDocuServiceDAO;

	private String jobId;

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	private String dsAuthHeader;

	private String accountId;

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getDsAuthHeader() {
		return dsAuthHeader;
	}

	public void setDsAuthHeader(String dsAuthHeader) {
		this.dsAuthHeader = dsAuthHeader;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.batch.item.ItemProcessor#process(java.lang.Object)
	 */
	@Override
	public EnvelopeDetails process(EnvelopeDetails envDetails) throws Exception {

		System.out.println("Entering DSNotificationProcessor.process()");

		String params = "ExpireEnabled- " + envDetails.getExpireEnabled() + " ExpireAfter-"
				+ envDetails.getExpireAfter() + " ExpireWarn- " + envDetails.getExpireWarn() + " ReminderEnabled- "
				+ envDetails.getReminderEnabled() + " ReminderDelay- " + envDetails.getReminderDelay()
				+ " ReminderFrequency- " + envDetails.getReminderFrequency() + " dsAuthHeader- " + dsAuthHeader;
		ResponseEntity<String> jsonResp = null;
		try {

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
			headers.add("Accept", "application/json");
			headers.add("X-DocuSign-Authentication", dsAuthHeader);

			objectMapper.setSerializationInclusion(Include.NON_NULL);
			objectMapper.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, false);
			objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);

			Notification notification = new Notification();

			if (null != envDetails.getReminderEnabled()) {
				Reminders reminders = new Reminders();
				reminders.setReminderDelay(String.valueOf(envDetails.getReminderDelay()));
				reminders.setReminderEnabled(String.valueOf(envDetails.getReminderEnabled()));
				reminders.setReminderFrequency(String.valueOf(envDetails.getReminderFrequency()));
				notification.setReminders(reminders);
			}

			if (null != envDetails.getExpireEnabled()) {
				Expirations expirations = new Expirations();
				expirations.setExpireAfter(String.valueOf(envDetails.getExpireAfter()));
				expirations.setExpireEnabled(String.valueOf(envDetails.getExpireEnabled()));
				expirations.setExpireWarn(String.valueOf(envDetails.getExpireWarn()));
				notification.setExpirations(expirations);
			}

			String msgBody = objectMapper.writeValueAsString(notification);

			HttpEntity<String> uri = new HttpEntity<String>(msgBody, headers);

			// https://demo.docusign.net/restapi/v2/accounts/{{AccountID}}/envelopes/fc5e81f0-2dee-4c84-b35f-7dfcd99e3df1/notification

			System.out.println("DSNotificationProcessor.process()- " + msgBody + " headers- " + headers + " params- "
					+ params + " accountId- " + accountId + " envId- " + envDetails.getEnvelopeId());

			jsonResp = restTemplate.exchange("https://demo.docusign.net/restapi/v2/accounts/" + accountId
					+ "/envelopes/" + envDetails.getEnvelopeId() + "/notification", HttpMethod.PUT, uri, String.class);

			logger.info("Body- " + jsonResp.getBody() + " RespHeaders- " + jsonResp.getHeaders());

			envDetails.setSuccess(true);
			envDetails.setTransMessage("Success");
			envelopesDocuServiceDAO.saveEnvelopeIds(jobId, envDetails.getEnvelopeId(), params, true, "success");
		} catch (Exception exp) {

			String transMessage = null;
			try {

				exp.printStackTrace();
				DSErrors error = null;
				if (exp instanceof HttpClientErrorException) {

					HttpClientErrorException clientExp = (HttpClientErrorException) exp;
					
					System.out.println("DSNotificationProcessor.process()- " + clientExp.getResponseBodyAsString());

					if(clientExp.getResponseBodyAsString().contains("errorCode")){

						error = objectMapper.readValue(clientExp.getResponseBodyAsString(), DSErrors.class);
					}
				}

				transMessage = exp.getMessage();
				if (null != error) {
					transMessage = error.getErrorCode() + "_" + error.getMessage();
				}
			} catch (Exception exp1) {
				exp1.printStackTrace();
			}

			envDetails.setSuccess(false);
			envDetails.setTransMessage(transMessage);
			envelopesDocuServiceDAO.saveEnvelopeIds(jobId, envDetails.getEnvelopeId(), params, false, transMessage);
		}

		return envDetails;
	}

}