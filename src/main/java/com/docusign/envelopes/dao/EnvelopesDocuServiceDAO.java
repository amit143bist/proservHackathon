/**
 * 
 */
package com.docusign.envelopes.dao;

/**
 * @author Amit.Bist
 *
 */
public interface EnvelopesDocuServiceDAO {

	String createJobId(String accountId, String jobTypeId);
	
	String updateJobId(String accountId, String jobId);
	
	String createConcurrentEnvelopeTasks();
	
	String fetchJobStatus(String jobId);
	
	String fetchEnvelopeIds(boolean transactionStatus);
	
	String saveEnvelopeIds(String jobId, String envelopeIds, String envelopeParams, boolean envelopeSuccessFlag, String envelopeTransactionComments);

	String updateEnvelopeIds(String jobId, String envelopeIds, String envelopeParams, String envelopeSuccessFlag, String envelopeTranComments);
}
