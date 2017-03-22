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
	
	String createConcurrentEnvelopeTasks();
	
	String fetchJobStatus(String jobId);
	
	String fetchEnvelopeIds(boolean transactionStatus);
	
}
