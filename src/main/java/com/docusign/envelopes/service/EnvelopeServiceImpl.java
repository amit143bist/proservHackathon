/**
 * 
 */
package com.docusign.envelopes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.docusign.envelope.ds.domain.EnvelopeDataList;
import com.docusign.envelope.executor.BatchExecutor;
import com.docusign.envelopes.dao.EnvelopesDocuServiceDAO;
import com.docusign.envelopes.dto.EnvelopeNotificationDTO;
import com.docusign.envelopes.dto.EnvelopeStatusDTO;

/**
 * @author Amit.Bist
 *
 */
public class EnvelopeServiceImpl implements EnvelopeService {

	@Autowired
	BatchExecutor batchExecutor;

	@Autowired
	EnvelopesDocuServiceDAO envelopesDocuServiceDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.docusign.envelopes.service.EnvelopeService#
	 * updateEnvelopesNotifications(java.util.List)
	 */
	@Override
	public String updateEnvelopesNotifications(List<EnvelopeDataList> envelopeNotificationDTOList,
			String accountId, String dsAuthHeader) {

		String jobId = envelopesDocuServiceDAO.createJobId(accountId, "NOTIFICATION_JOB");
		batchExecutor.asyncCall(jobId, envelopeNotificationDTOList, null, accountId, dsAuthHeader);

		return jobId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.docusign.envelopes.service.EnvelopeService#
	 * updateEnvelopesNotificationsUsingCSVs(java.util.List)
	 */
	@Override
	public String updateEnvelopesNotificationsUsingCSVs(List<MultipartFile> multiPartFiles, String accountId,
			String dsAuthHeader) {

		String jobId = envelopesDocuServiceDAO.createJobId(accountId, "NOTIFICATION_JOB_CSV");
		batchExecutor.asyncCall(jobId, null, multiPartFiles, accountId, dsAuthHeader);

		return jobId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.docusign.envelopes.service.EnvelopeService#updateEnvelopesStatus(java
	 * .util.List)
	 */
	@Override
	public String updateEnvelopesStatus(List<EnvelopeStatusDTO> envelopeStatusDTOList, String accountId,
			String dsAuthHeader) {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.docusign.envelopes.service.EnvelopeService#
	 * updateEnvelopesStatusUsingCSVs(java.util.List)
	 */
	@Override
	public String updateEnvelopesStatusUsingCSVs(List<MultipartFile> multiPartFiles, String accountId,
			String dsAuthHeader) {

		return null;
	}

}
