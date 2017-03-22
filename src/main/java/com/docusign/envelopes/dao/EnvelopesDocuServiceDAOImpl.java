/**
 * 
 */
package com.docusign.envelopes.dao;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.docusign.envelopes.db.domain.EnvelopeScheduledTask;

/**
 * @author Amit.Bist
 *
 */
public class EnvelopesDocuServiceDAOImpl extends AbstractDAO implements EnvelopesDocuServiceDAO {

	private static final Logger logger = LoggerFactory.getLogger(EnvelopesDocuServiceDAOImpl.class);

	@Override
	public String createJobId(String accountId, String jobTypeId) {

		String jobId = UUID.randomUUID().toString();
		
		logger.info("In EnvelopesDocuServiceDAOImpl.createJobId()- " + jobId);
		
		EnvelopeScheduledTask envelopeScheduledTask = new EnvelopeScheduledTask();
		
		envelopeScheduledTask.setEnvelopeJobId(jobId);
		
		Timestamp jobTime = new Timestamp(Calendar.getInstance().getTime().getTime());
		envelopeScheduledTask.setEnvelopeJobStartDateTime(jobTime);
		
		envelopeScheduledTask.setEnvelopeJobCreateDateTime(jobTime);
		
		envelopeScheduledTask.setEnvelopeJobTypeId(jobTypeId);
		
		envelopeScheduledTask.setEnvelopeJobAccountId(accountId);
		
		getSession().save(envelopeScheduledTask);
		
		return jobId;
	}

	@Override
	public String createConcurrentEnvelopeTasks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String fetchJobStatus(String jobId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String fetchEnvelopeIds(boolean transactionStatus) {
		// TODO Auto-generated method stub
		return null;
	}

}
