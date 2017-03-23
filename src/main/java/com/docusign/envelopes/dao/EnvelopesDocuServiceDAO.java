/**
 * 
 */
package com.docusign.envelopes.dao;

import java.util.List;

import com.docusign.envelopes.db.domain.EnvelopeConcurrentLog;

/**
 * @author Amit.Bist
 *
 */
public interface EnvelopesDocuServiceDAO {

	String createJobId(String accountId, String jobTypeId);
	
	String updateJobId(String accountId, String jobId);
	
	String createConcurrentEnvelopeTasks();
	
	String fetchJobStatus(String jobId);
	
	List<EnvelopeConcurrentLog> fetchEnvelopeIds(String transactionStatus, String jobId);
	
	String saveEnvelopeIds(String jobId, String envelopeIds, String envelopeParams, boolean envelopeSuccessFlag, String envelopeTransactionComments);

	String updateEnvelopeIds(String jobId, String envelopeIds, String envelopeParams, String envelopeSuccessFlag, String envelopeTranComments);
}
