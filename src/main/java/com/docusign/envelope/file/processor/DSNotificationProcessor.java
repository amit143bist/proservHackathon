/**
 * 
 */
package com.docusign.envelope.file.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.docusign.envelope.ds.domain.Expirations;
import com.docusign.envelope.ds.domain.Notification;
import com.docusign.envelope.ds.domain.Reminders;
import com.docusign.envelope.file.domain.EnvelopeDetails;
import com.docusign.envelopes.dao.EnvelopesDocuServiceDAO;
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

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("X-DocuSign-Authentication", dsAuthHeader);

		objectMapper.setSerializationInclusion(Include.NON_NULL);
		objectMapper.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, false);
		objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);

		Reminders reminders = new Reminders();
		reminders.setReminderDelay(String.valueOf(envDetails.getReminderDelay()));
		reminders.setReminderEnabled(String.valueOf(envDetails.getReminderEnabled()));
		reminders.setReminderFrequency(String.valueOf(envDetails.getReminderFrequency()));

		Expirations expirations = new Expirations();
		expirations.setExpireAfter(String.valueOf(envDetails.getExpireAfter()));
		expirations.setExpireEnabled(String.valueOf(envDetails.getExpireEnabled()));
		expirations.setExpireWarn(String.valueOf(envDetails.getExpireWarn()));

		Notification notification = new Notification();
		notification.setExpirations(expirations);
		notification.setReminders(reminders);

		String msgBody = objectMapper.writeValueAsString(notification);

		HttpEntity<String> uri = new HttpEntity<String>(msgBody, headers);

		// https://demo.docusign.net/restapi/v2/accounts/{{AccountID}}/envelopes/fc5e81f0-2dee-4c84-b35f-7dfcd99e3df1/notification

		String params = "ExpireEnabled- " + envDetails.getExpireEnabled() + 
				" ExpireAfter-" + envDetails.getExpireAfter() + 
				" ExpireWarn- " + envDetails.getExpireWarn() + 
				" ReminderEnabled- " + envDetails.getReminderEnabled() + 
				" ReminderDelay- " + envDetails.getReminderDelay() + 
				" ReminderFrequency- " + envDetails.getReminderFrequency();
		try{
			
			ResponseEntity<String> jsonResp = restTemplate.exchange("https://demo.docusign.net/restapi/v2/accounts/"
					+ accountId + "/envelopes" + envDetails.getEnvelopeId() + "/notification", HttpMethod.PUT, uri,
					String.class);
			
			logger.info("Body- " + jsonResp.getBody() + " RespHeaders- " + jsonResp.getHeaders());
			
			envelopesDocuServiceDAO.saveEnvelopeIds(jobId, envDetails.getEnvelopeId(), params, true, "success");
		}catch(Exception exp){
			
			envelopesDocuServiceDAO.saveEnvelopeIds(jobId, envDetails.getEnvelopeId(), params, false, exp.getMessage());
		}
		
		
		return envDetails;
	}

}