package com.docusign.mvc.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.docusign.envelope.ds.domain.Track;
import com.docusign.envelopes.dao.EnvelopesDocuServiceDAO;
import com.docusign.envelopes.db.domain.EnvelopeConcurrentLog;
import com.docusign.envelopes.service.EnvelopeService;
import com.docusign.mvc.model.EnvelopeData;
import com.docusign.mvc.model.EnvelopeUpdateStatus;

@RestController
public class EnvelopeBulkUpdateController {

	private static final Logger logger = LoggerFactory.getLogger(EnvelopeBulkUpdateController.class);

	@Autowired
	EnvelopeService envelopeService;

	@Autowired
	EnvelopesDocuServiceDAO envelopeServiceDao;

	@RequestMapping("/")
	public String welcome() {// Welcome page, non-rest

		System.out.println("EnvelopeBulkUpdateController.welcome()");
		return "Welcome to RestTemplate Example.";
	}

	/*
	 * @RequestMapping("/hello/{player}") public Message message(@PathVariable
	 * String player) {//REST Endpoint.
	 * 
	 * Message msg = new Message(player, "Hello " + player); return msg; }
	 */

	/**
	 * <p>
	 * Update envelopes notification.
	 * </p>
	 * 
	 * <p>
	 * Expected HTTP POST and request '/person/form'.
	 * </p>
	 */
	@RequestMapping(value = "/account/{accountId}/envelopes/notification", method = RequestMethod.PUT, produces = "application/json")
	public EnvelopeUpdateStatus updateEnvelopesNotification(@RequestBody EnvelopeData envelopeData, 
			@PathVariable String accountId, HttpServletRequest request) {

		String authHeader = request.getHeader("X-DocuSign-Authentication");
		
		String jobId = envelopeService.updateEnvelopesNotifications(envelopeData.getEnvelopeDataList(), accountId,
				authHeader);

		EnvelopeUpdateStatus response = new EnvelopeUpdateStatus();
		response.setBatchId(jobId);
		response.setStatus("In Progress");

		return response;
	}

	/**
	 * <p>
	 * Update envelopes notification.
	 * </p>
	 * 
	 * <p>
	 * Expected HTTP POST and request '/person/form'.
	 * </p>
	 */
	@RequestMapping(value = "/account/{accountId}/envelopes/batch/{batchId}", method = RequestMethod.GET, produces = "application/json")
	public EnvelopeUpdateStatus retrieveBatchStatus(@PathVariable String accountId, @PathVariable String batchId) {

		EnvelopeUpdateStatus response = new EnvelopeUpdateStatus();

		response.setBatchId(batchId);
		response.setStatus(envelopeServiceDao.fetchJobStatus(batchId));

		return response;
	}

	@RequestMapping(value = "/account/{accountId}/envelopes/batch/{batchId}/records", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<EnvelopeConcurrentLog> fetchFailedEnvelopes(@PathVariable String accountId,
			@PathVariable String batchId, @RequestParam(value = "status", required = false) String status) {

		List<EnvelopeConcurrentLog> envelopeConcurrentLogList = envelopeServiceDao.fetchEnvelopeIds(status, batchId);

		return envelopeConcurrentLogList;
	}

	@RequestMapping(value = "/account/{accountId}/envelopes/bulk", method = RequestMethod.POST, consumes = {
			"multipart/form-data" })
	@ResponseBody
	public EnvelopeUpdateStatus bulkUpdateService(@RequestPart("track") Track track, @PathVariable String accountId,
			HttpServletRequest request, @RequestPart("file") MultipartFile... files) {

		
		System.out.println("EnvelopeBulkUpdateController.bulkUpdateService()- " + request.getHeader("Content-Transfer-Encoding"));
		String authHeader = request.getHeader("X-DocuSign-Authentication");

		String jobId = envelopeService.updateEnvelopesNotificationsUsingCSVs(Arrays.asList(files), accountId,
				authHeader);

		logger.info("jobId in EnvelopeBulkUpdateController.executeSampleService()- " + jobId);

		EnvelopeUpdateStatus response = new EnvelopeUpdateStatus();
		response.setBatchId(jobId);
		response.setStatus("In Progress");
		return response;
	}

}