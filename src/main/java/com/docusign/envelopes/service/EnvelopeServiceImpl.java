/**
 * 
 */
package com.docusign.envelopes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.docusign.envelopes.batch.executor.BatchExecutor;
import com.docusign.envelopes.dao.EnvelopesDocuServiceDAO;
import com.docusign.envelopes.ds.domain.EnvelopeDataList;

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
	public String updateEnvelopesNotifications(List<EnvelopeDataList> envelopeNotificationDTOList, String accountId,
			String dsAuthHeader) {

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
	 * @see com.docusign.envelopes.service.EnvelopeService#
	 * updateEnvelopesStatusUsingCSVs(java.util.List)
	 */
	@Override
	public String updateEnvelopesStatusUsingCSVs(List<MultipartFile> multiPartFiles, String accountId,
			String dsAuthHeader) {

		return null;
	}

}