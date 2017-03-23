/**
 * 
 */
package com.docusign.envelope.executor;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

import com.docusign.envelopes.dao.EnvelopesDocuServiceDAO;
import com.docusign.envelopes.dto.EnvelopeNotificationDTO;

/**
 * @author Amit.Bist
 *
 */
public class BatchExecutor implements ApplicationContextAware {

	public static Map<String, Object> customStorage = new HashMap<String, Object>();

	@Autowired
	EnvelopesDocuServiceDAO envelopesDocuServiceDAO;

	/*
	 * @Autowired private LineMapper<EnvelopeDetails> lineMapper;
	 */

	private class BatchTask implements Runnable {

		private String jobId;

		private String accountId;

		private String dsAuthHeader;

		private List<MultipartFile> multiPartFiles;

		private List<EnvelopeNotificationDTO> envelopeNotificationDTOList;

		public BatchTask(String jobId, List<EnvelopeNotificationDTO> envelopeNotificationDTOList,
				List<MultipartFile> multiPartFiles, String accountId, String dsAuthHeader) {
			this.jobId = jobId;
			this.accountId = accountId;
			this.dsAuthHeader = dsAuthHeader;
			this.multiPartFiles = multiPartFiles;
			this.envelopeNotificationDTOList = envelopeNotificationDTOList;
		}

		public void run() {
			System.out.println(jobId + " multiPartFiles- " + multiPartFiles + " envelopeNotificationDTOList- "
					+ envelopeNotificationDTOList);

			BatchExecutor.customStorage.put("multiPartFiles", multiPartFiles);
			BatchExecutor.customStorage.put("envelopeNotificationDTOList", envelopeNotificationDTOList);

			JobLauncher jobLauncher = (JobLauncher) applicationContext.getBean("jobLauncher");
			Job job = (Job) applicationContext.getBean("notificationProcessingJob");

			Map<String, JobParameter> parametersMap = new LinkedHashMap<String, JobParameter>();

			parametersMap.put("jobId", new JobParameter(jobId));
			parametersMap.put("accountId", new JobParameter(accountId));
			parametersMap.put("dsAuthHeader", new JobParameter(dsAuthHeader));
			parametersMap.put("outputDataFile", new JobParameter("C://Softwares//failed.csv"));

			JobParameters jobParameters = new JobParameters(parametersMap);

			envelopesDocuServiceDAO.updateJobId(accountId, jobId);

			try {
				JobExecution execution = jobLauncher.run(job, jobParameters);
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

			/*
			 * if(null == multiPartFiles){
			 * 
			 * 
			 * 
			 * }else{
			 * 
			 * for(MultipartFile file:multiPartFiles){
			 * 
			 * InputStream is = null; BufferedReader bfReader = null; try {
			 * byte[] decodedBytes = Base64.decodeBase64(file.getBytes()); is =
			 * new ByteArrayInputStream(decodedBytes); bfReader = new
			 * BufferedReader(new InputStreamReader(is)); String temp = null;
			 * int count = 0; while((temp = bfReader.readLine()) != null){
			 * System.out.println(temp); count++; if(count > 1){ EnvelopeDetails
			 * envelopeDetails = lineMapper.mapLine(temp, count); String params
			 * = "ExpireEnabled- " + envelopeDetails.getExpireEnabled() +
			 * " ExpireAfter-" + envelopeDetails.getExpireAfter() +
			 * " ExpireWarn- " + envelopeDetails.getExpireWarn() +
			 * " ReminderEnabled- " + envelopeDetails.getReminderEnabled() +
			 * " ReminderDelay- " + envelopeDetails.getReminderDelay() +
			 * " ReminderFrequency- " + envelopeDetails.getReminderFrequency();
			 * 
			 * envelopesDocuServiceDAO.saveEnvelopeIds(jobId,
			 * envelopeDetails.getEnvelopeId(), params); } } } catch (Exception
			 * e) { e.printStackTrace(); } finally { try{ if(is != null)
			 * is.close(); } catch (Exception ex){
			 * 
			 * } } }
			 * 
			 * }
			 */
		}

	}

	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	public BatchExecutor(ThreadPoolTaskExecutor threadPoolTaskExecutor) {
		this.threadPoolTaskExecutor = threadPoolTaskExecutor;
	}

	public void asyncCall(String jobId, List<EnvelopeNotificationDTO> envelopeNotificationDTOList,
			List<MultipartFile> multiPartFiles, String accountId, String dsAuthHeader) {
		for (int i = 0; i < 25; i++) {
			threadPoolTaskExecutor.execute(
					new BatchTask(jobId, envelopeNotificationDTOList, multiPartFiles, accountId, dsAuthHeader));
		}
	}

	ApplicationContext applicationContext = null;

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {

		this.applicationContext = applicationContext;
	}
}