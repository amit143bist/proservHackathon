package com.docusign.proserv;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.docusign.envelope.ds.domain.Track;
import com.docusign.envelopes.service.EnvelopeService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	EnvelopeService envelopeService;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/time", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value = "/executesampleservice", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	@ResponseBody
	public boolean executeSampleService(@RequestPart("track") Track track,
			@RequestPart("file") MultipartFile... files) {

		int fileLength = files.length;

		logger.info(" fileLength in HomeController.executeSampleService()- " + fileLength);
		
		String jobId = envelopeService.updateEnvelopesNotificationsUsingCSVs(Arrays.asList(files), "1764240", "{\"Username\": \"amitkumar.bist+test@gmail.com\", \"Password\":\"testing1\", \"IntegratorKey\":\"16f81d9e-e9ee-408d-bc60-d6e1aecd9756\"}");
		
		logger.info("jobId in HomeController.executeSampleService()- " + jobId);
 
		for (MultipartFile file : files) {
			
			logger.info(" Name- " + file.getOriginalFilename() + " ContentType- " + file.getContentType());

			try {

				byte[] decodedBytes = Base64.decodeBase64(file.getBytes());

				String strFilePath = "C://Softwares//" + file.getOriginalFilename();
				FileOutputStream fos = new FileOutputStream(strFilePath);

				fos.write(decodedBytes);

				fos.close();
				
				System.out.println("---- HomeController.executeSampleService()----  ");
				
				InputStream is = null;
				BufferedReader bfReader = null;
				try {
					is = new ByteArrayInputStream(decodedBytes);
					bfReader = new BufferedReader(new InputStreamReader(is));
					String temp = null;
					while((temp = bfReader.readLine()) != null){
						System.out.println(temp);
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try{
						if(is != null) is.close();
					} catch (Exception ex){
						
					}
				}
				
				

			} catch (IOException e) {

				e.printStackTrace();
			}
		}

		return true;
	}
	
}
