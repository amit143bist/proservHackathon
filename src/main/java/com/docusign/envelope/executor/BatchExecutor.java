/**
 * 
 */
package com.docusign.envelope.executor;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.multipart.MultipartFile;

import com.docusign.envelope.file.processor.DSEnvelopeNotificationProcessor;
import com.docusign.envelopes.dao.EnvelopesDocuServiceDAO;
import com.docusign.mvc.model.EnvelopeDataList;
import com.docusign.mvc.model.Notification;

/**
 * @author Amit.Bist
 *
 */
public class BatchExecutor implements ApplicationContextAware {

	private static final Logger logger = LoggerFactory.getLogger(BatchExecutor.class);

	public static Map<String, Object> customStorage = new HashMap<String, Object>();

	@Autowired
	EnvelopesDocuServiceDAO envelopesDocuServiceDAO;

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	Job notificationProcessingJob;

	@Autowired
	DSEnvelopeNotificationProcessor dsEnvelopeNotificationProcessor;

	/*
	 * @Autowired private LineMapper<EnvelopeDetails> lineMapper;
	 */

	private class BatchTask implements Runnable {

		private String jobId;

		private String accountId;

		private String dsAuthHeader;

		private List<MultipartFile> multiPartFiles;

		private List<EnvelopeDataList> envelopeNotificationDTOList;

		public BatchTask(String jobId, List<EnvelopeDataList> envelopeNotificationDTOList,
				List<MultipartFile> multiPartFiles, String accountId, String dsAuthHeader) {
			this.jobId = jobId;
			this.accountId = accountId;
			this.dsAuthHeader = dsAuthHeader;
			this.multiPartFiles = multiPartFiles;
			this.envelopeNotificationDTOList = envelopeNotificationDTOList;
		}

		public void run() {

			logger.info(jobId + " multiPartFiles- " + multiPartFiles + " envelopeNotificationDTOList- "
					+ envelopeNotificationDTOList);
			if (null != multiPartFiles) {

				BatchExecutor.customStorage.put("multiPartFiles", multiPartFiles);
				BatchExecutor.customStorage.put("envelopeNotificationDTOList", envelopeNotificationDTOList);

				// JobLauncher jobLauncher = (JobLauncher)
				// applicationContext.getBean("jobLauncher");
				// Job job = (Job)
				// applicationContext.getBean("notificationProcessingJob");

				logger.info("jobLauncher- " + jobLauncher + " job- " + notificationProcessingJob
						+ " applicationContext- " + applicationContext);

				System.out.println("BatchExecutor.BatchTask.run()- " + "jobLauncher- " + jobLauncher + " job- "
						+ notificationProcessingJob + " applicationContext- " + applicationContext);

				Map<String, JobParameter> parametersMap = new LinkedHashMap<String, JobParameter>();

				parametersMap.put("jobId", new JobParameter(jobId));
				parametersMap.put("accountId", new JobParameter(accountId));
				parametersMap.put("dsAuthHeader", new JobParameter(dsAuthHeader));
				parametersMap.put("outputDataFile", new JobParameter("C://Softwares"));

				parametersMap.put("reminderEnabled", new JobParameter("true"));
				parametersMap.put("expirationEnabled", new JobParameter("false"));

				JobParameters jobParameters = new JobParameters(parametersMap);

				try {
					JobExecution execution = jobLauncher.run(notificationProcessingJob, jobParameters);

					envelopesDocuServiceDAO.updateJobId(accountId, jobId);
				} catch (JobExecutionAlreadyRunningException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JobRestartException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JobInstanceAlreadyCompleteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JobParametersInvalidException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {

				dsEnvelopeNotificationProcessor.setAccountId(accountId);
				dsEnvelopeNotificationProcessor.setJobId(jobId);
				dsEnvelopeNotificationProcessor.setDsAuthHeader(dsAuthHeader);

				for (EnvelopeDataList envelopeDataList : envelopeNotificationDTOList) {
					Notification notification = envelopeDataList.getNotification();
					try {
						dsEnvelopeNotificationProcessor.process(notification, envelopeDataList.getEnvelopeId());

						envelopesDocuServiceDAO.updateJobId(accountId, jobId);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}

		}

	}

	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	public BatchExecutor(ThreadPoolTaskExecutor threadPoolTaskExecutor) {
		this.threadPoolTaskExecutor = threadPoolTaskExecutor;
	}

	public void asyncCall(String jobId, List<EnvelopeDataList> envelopeNotificationDTOList,
			List<MultipartFile> multiPartFiles, String accountId, String dsAuthHeader) {
		threadPoolTaskExecutor
				.execute(new BatchTask(jobId, envelopeNotificationDTOList, multiPartFiles, accountId, dsAuthHeader));
	}

	ApplicationContext applicationContext = null;

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {

		this.applicationContext = applicationContext;
	}
}