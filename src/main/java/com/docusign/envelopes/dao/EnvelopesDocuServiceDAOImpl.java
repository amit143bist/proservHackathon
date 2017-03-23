/**
 * 
 */
package com.docusign.envelopes.dao;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.UUID;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.docusign.envelopes.db.domain.EnvelopeConcurrentLog;
import com.docusign.envelopes.db.domain.EnvelopeConcurrentLogPK;
import com.docusign.envelopes.db.domain.EnvelopeScheduledTask;

/**
 * @author Amit.Bist
 *
 */
@Transactional(rollbackFor = Exception.class)
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
	public String updateJobId(String accountId, String jobId) {

		Timestamp ts = new Timestamp(Calendar.getInstance().getTimeInMillis());
		String queryStr = "update EnvelopeScheduledTask set EnvelopeJobEndDateTime = " + ts + ", "
				+ " EnvelopeJobLastModifiedDateTime = " + ts + " where EnvelopeJobId = " + jobId
				+ " and EnvelopeJobAccountId = " + accountId;

		Query query = getSession().createQuery(queryStr);
		int count = query.executeUpdate();
		
		return String.valueOf(count);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.docusign.envelopes.dao.EnvelopesDocuServiceDAO#saveEnvelopeIds(java.
	 * lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String saveEnvelopeIds(String jobId, String envelopeIds, String envelopeParams, boolean envelopeSuccessFlag,
			String envelopeTransactionComments) {

		EnvelopeConcurrentLog envelopeConcurrentLog = new EnvelopeConcurrentLog();
		EnvelopeConcurrentLogPK pk = new EnvelopeConcurrentLogPK();
		pk.setEnvelopeId(envelopeIds);
		pk.setEnvelopeJobId(jobId);

		envelopeConcurrentLog.setEnvelopeConcurrentLogPK(pk);
		envelopeConcurrentLog.setEnvelopeParams(envelopeParams);
		envelopeConcurrentLog.setEnvelopeSuccessFlag(envelopeSuccessFlag);
		envelopeConcurrentLog.setEnvelopeTransactionComments(envelopeTransactionComments);

		getSession().save(envelopeConcurrentLog);
		return "true";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.docusign.envelopes.dao.EnvelopesDocuServiceDAO#updateEnvelopeIds(java
	 * .lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public String updateEnvelopeIds(String jobId, String envelopeIds, String envelopeParams, String envelopeSuccessFlag,
			String envelopeTranComments) {

		String queryStr = "update EnvelopeConcurrentLog set envelopeSuccessFlag = " + envelopeSuccessFlag + ", "
				+ " EnvelopeTransactionComments = " + envelopeTranComments + " where EnvelopeJobId = " + jobId
				+ " and EnvelopeId = " + envelopeIds;

		Query query = getSession().createQuery(queryStr);
		int count = query.executeUpdate();
		return null;
	}

}
