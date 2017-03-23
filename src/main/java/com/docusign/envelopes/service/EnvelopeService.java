/**
 * 
 */
package com.docusign.envelopes.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.docusign.envelopes.dto.EnvelopeStatusDTO;
import com.docusign.mvc.model.EnvelopeDataList;

/**
 * @author Amit.Bist
 *
 */
public interface EnvelopeService {
	
	public String updateEnvelopesNotifications(List<EnvelopeDataList> envelopeNotificationDTOList, String accountId, String dsAuthHeader);
	
	public String updateEnvelopesNotificationsUsingCSVs(List<MultipartFile> multiPartFiles, String accountId, String dsAuthHeader);
	
	public String updateEnvelopesStatus(List<EnvelopeStatusDTO> envelopeStatusDTOList, String accountId, String dsAuthHeader);
	
	public String updateEnvelopesStatusUsingCSVs(List<MultipartFile> multiPartFiles, String accountId, String dsAuthHeader);
	
}