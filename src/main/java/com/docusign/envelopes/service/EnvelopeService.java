/**
 * 
 */
package com.docusign.envelopes.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.docusign.envelopes.dto.EnvelopeNotificationDTO;
import com.docusign.envelopes.dto.EnvelopeStatusDTO;

/**
 * @author Amit.Bist
 *
 */
public interface EnvelopeService {
	
	public String updateEnvelopesNotifications(List<EnvelopeNotificationDTO> envelopeNotificationDTOList, String accountId);
	
	public String updateEnvelopesNotificationsUsingCSVs(List<MultipartFile> multiPartFiles, String accountId);
	
	public String updateEnvelopesStatus(List<EnvelopeStatusDTO> envelopeStatusDTOList, String accountId);
	
	public String updateEnvelopesStatusUsingCSVs(List<MultipartFile> multiPartFiles, String accountId);

}