/**
 * 
 */
package com.docusign.envelope.executor;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.docusign.envelopes.dto.EnvelopeNotificationDTO;

/**
 * @author Amit.Bist
 *
 */
public class BatchExecutor implements Runnable {

	private String jobId;
	
	private List<MultipartFile> multiPartFiles; 
	
	private List<EnvelopeNotificationDTO> envelopeNotificationDTOList;
	/**
	 * 
	 */
	public BatchExecutor(String jobId, List<EnvelopeNotificationDTO> envelopeNotificationDTOList,
			List<MultipartFile> multiPartFiles) {
		
		this.jobId = jobId;
		this.multiPartFiles = multiPartFiles;
		this.envelopeNotificationDTOList = envelopeNotificationDTOList;

	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
