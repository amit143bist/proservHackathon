package com.docusign.mvc.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.docusign.envelope.ds.domain.Track;
import com.docusign.envelopes.service.EnvelopeService;
import com.docusign.mvc.model.EnvelopeData;
import com.docusign.mvc.model.EnvelopeUpdateStatus;

@RestController
public class EnvelopeBulkUpdateController {

	private static final Logger logger = LoggerFactory.getLogger(EnvelopeBulkUpdateController.class);

	@Autowired
	EnvelopeService envelopeService;

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
	 * Saves a person.
	 * </p>
	 * 
	 * <p>
	 * Expected HTTP POST and request '/person/form'.
	 * </p>
	 */
	@RequestMapping(value = "/account/{accountId}/envelopes/notification", method = RequestMethod.PUT, produces = "application/json")
	public EnvelopeUpdateStatus form(EnvelopeData envelopeData, @PathVariable String accountId) {

		EnvelopeUpdateStatus response = new EnvelopeUpdateStatus();
		response.setBatchId("705c766c-b7c0-4c3e-8ed9-6f2eabdba68e");

		return response;
	}

	@RequestMapping(value = "/account/{accountId}/envelopes/executesampleservice", method = RequestMethod.POST, consumes = {
			"multipart/form-data" })
	@ResponseBody
	public EnvelopeUpdateStatus executeSampleService(@RequestPart("track") Track track, @PathVariable String accountId,
			@RequestPart("file") MultipartFile... files) {

		int fileLength = files.length;

		logger.info(" fileLength in EnvelopeBulkUpdateController.executeSampleService()- " + fileLength);

		String jobId = envelopeService.updateEnvelopesNotificationsUsingCSVs(Arrays.asList(files), accountId,
				"{\"Username\": \"amitkumar.bist+test@gmail.com\", \"Password\":\"testing1\", \"IntegratorKey\":\"16f81d9e-e9ee-408d-bc60-d6e1aecd9756\"}");

		logger.info("jobId in EnvelopeBulkUpdateController.executeSampleService()- " + jobId);

		for (MultipartFile file : files) {

			logger.info(" Name- " + file.getOriginalFilename() + " ContentType- " + file.getContentType());

			try {

				byte[] decodedBytes = Base64.decodeBase64(file.getBytes());

				String strFilePath = "C://Softwares//" + file.getOriginalFilename();
				FileOutputStream fos = new FileOutputStream(strFilePath);

				fos.write(decodedBytes);

				fos.close();

				System.out.println("---- EnvelopeBulkUpdateController.executeSampleService()----  ");

				InputStream is = null;
				BufferedReader bfReader = null;
				try {
					is = new ByteArrayInputStream(decodedBytes);
					bfReader = new BufferedReader(new InputStreamReader(is));
					String temp = null;
					while ((temp = bfReader.readLine()) != null) {
						System.out.println(temp);
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						if (is != null)
							is.close();
					} catch (Exception ex) {

					}
				}

			} catch (IOException e) {

				e.printStackTrace();
			}
		}

		EnvelopeUpdateStatus response = new EnvelopeUpdateStatus();
		response.setBatchId(jobId);
		return response;
	}

}
