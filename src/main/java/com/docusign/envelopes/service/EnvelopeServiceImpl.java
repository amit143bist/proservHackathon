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
public class EnvelopeServiceImpl implements EnvelopeService {

	/**
	 * 
	 */
	public EnvelopeServiceImpl() {

	}

	/* (non-Javadoc)
	 * @see com.docusign.envelopes.service.EnvelopeService#updateEnvelopesNotifications(java.util.List)
	 */
	@Override
	public String updateEnvelopesNotifications(List<EnvelopeNotificationDTO> envelopeNotificationDTOList, String accountId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.docusign.envelopes.service.EnvelopeService#updateEnvelopesNotificationsUsingCSVs(java.util.List)
	 */
	@Override
	public String updateEnvelopesNotificationsUsingCSVs(List<MultipartFile> multiPartFiles, String accountId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.docusign.envelopes.service.EnvelopeService#updateEnvelopesStatus(java.util.List)
	 */
	@Override
	public String updateEnvelopesStatus(List<EnvelopeStatusDTO> envelopeStatusDTOList, String accountId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.docusign.envelopes.service.EnvelopeService#updateEnvelopesStatusUsingCSVs(java.util.List)
	 */
	@Override
	public String updateEnvelopesStatusUsingCSVs(List<MultipartFile> multiPartFiles, String accountId) {
		// TODO Auto-generated method stub
		return null;
	}

}
