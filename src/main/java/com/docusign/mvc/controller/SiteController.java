package com.docusign.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SiteController {

	@RequestMapping("/BulkRadmin")
	public String welcome() {// Welcome page, non-rest

		return "BulkRadmin";
	}
}
