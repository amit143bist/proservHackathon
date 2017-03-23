/**
 * 
 */
package com.docusign.envelope.file.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.docusign.envelope.ds.domain.DSErrors;
import com.docusign.envelopes.dao.EnvelopesDocuServiceDAO;
import com.docusign.mvc.model.Notification;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * @author Amit.Bist
 *
 */
public class DSEnvelopeNotificationProcessor {
	
	private static final Logger logger = LoggerFactory.getLogger(DSEnvelopeNotificationProcessor.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	EnvelopesDocuServiceDAO envelopesDocuServiceDAO;
	
	private String jobId;
	
	private String dsAuthHeader;

	private String accountId;

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getDsAuthHeader() {
		return dsAuthHeader;
	}

	public void setDsAuthHeader(String dsAuthHeader) {
		this.dsAuthHeader = dsAuthHeader;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	public void process(Notification notification, String envelopeId) throws Exception {
	
		String params = "ExpireEnabled- " + notification.getExpirations().getExpireEnabled() + " ExpireAfter-"
				+ notification.getExpirations().getExpireAfter() + " ExpireWarn- " + notification.getExpirations().getExpireWarn() 
				+ " ReminderEnabled- " + notification.getReminders().getReminderEnabled() + " ReminderDelay- " + notification.getReminders().getReminderDelay()
				+ " ReminderFrequency- " + notification.getReminders().getReminderFrequency() + " dsAuthHeader- " + dsAuthHeader;
		ResponseEntity<String> jsonResp = null;
		try {

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
			headers.add("Accept", "application/json");
			headers.add("X-DocuSign-Authentication", dsAuthHeader);

			objectMapper.setSerializationInclusion(Include.NON_NULL);
			objectMapper.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, false);
			objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);

			String msgBody = objectMapper.writeValueAsString(notification);

			HttpEntity<String> uri = new HttpEntity<String>(msgBody, headers);

			// https://demo.docusign.net/restapi/v2/accounts/{{AccountID}}/envelopes/fc5e81f0-2dee-4c84-b35f-7dfcd99e3df1/notification

			System.out.println("DSEnvelopeNotificationProcessor.process()- " + msgBody + " headers- " + headers + " params- "
					+ params + " accountId- " + accountId + " envId- " + envelopeId);

			jsonResp = restTemplate.exchange("https://demo.docusign.net/restapi/v2/accounts/" + accountId
					+ "/envelopes/" + envelopeId + "/notification", HttpMethod.PUT, uri, String.class);

			logger.info("Body- " + jsonResp.getBody() + " RespHeaders- " + jsonResp.getHeaders());

			envelopesDocuServiceDAO.saveEnvelopeIds(jobId, envelopeId, params, true, "success");
		} catch (Exception exp) {

			String transMessage = null;
			try {

				DSErrors error = null;
				if (exp instanceof HttpClientErrorException) {

					HttpClientErrorException clientExp = (HttpClientErrorException) exp;
					
					System.out.println("DSEnvelopeNotificationProcessor.process()- " + clientExp.getResponseBodyAsString());

					error = objectMapper.readValue(clientExp.getResponseBodyAsString(), DSErrors.class);
				}

				transMessage = exp.getMessage();
				if (null != error) {
					transMessage = error.getErrorCode() + "_" + error.getMessage();
				}
			} catch (Exception exp1) {
				exp1.printStackTrace();
			}

			envelopesDocuServiceDAO.saveEnvelopeIds(jobId, envelopeId, params, false, transMessage);
		}
	}

}
