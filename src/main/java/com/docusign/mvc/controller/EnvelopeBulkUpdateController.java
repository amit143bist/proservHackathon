package com.docusign.mvc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.docusign.mvc.model.EnvelopeData;
import com.docusign.mvc.model.EnvelopeUpdateStatus;

@RestController
public class EnvelopeBulkUpdateController {

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
	@RequestMapping(value = "/envelopes/notification", method = RequestMethod.PUT, produces="application/json")
	public EnvelopeUpdateStatus form(EnvelopeData envelopeData) {

		EnvelopeUpdateStatus response = new EnvelopeUpdateStatus();
		response.setBatchId("705c766c-b7c0-4c3e-8ed9-6f2eabdba68e");

		return response;
	}

}
