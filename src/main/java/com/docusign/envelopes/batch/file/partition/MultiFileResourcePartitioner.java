/**
 * 
 */
package com.docusign.envelopes.batch.file.partition;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.web.multipart.MultipartFile;

import com.docusign.envelopes.batch.executor.BatchExecutor;

/**
 * @author Amit.Bist
 *
 */
public class MultiFileResourcePartitioner implements Partitioner {

	private static final Logger logger = LoggerFactory.getLogger(MultiFileResourcePartitioner.class);

	/*
	 * private MultipartFile[] multipartFiles;
	 * 
	 * public MultipartFile[] getMultipartFiles() { return multipartFiles; }
	 * 
	 * public void setMultipartFiles(MultipartFile[] multipartFiles) {
	 * this.multipartFiles = multipartFiles; }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.batch.core.partition.support.Partitioner#partition(
	 * int)
	 */
	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {

		List<MultipartFile> multiPartFiles = (List<MultipartFile>) BatchExecutor.customStorage.get("multiPartFiles");

		logger.info("multiPartFiles in partitition- " + multiPartFiles);

		Map<String, ExecutionContext> partitionMap = new HashMap<String, ExecutionContext>();

		for (MultipartFile file : multiPartFiles) {
			ExecutionContext context = new ExecutionContext();
			
			
			logger.info("file.getOriginalFilename()- " + file.getOriginalFilename());

			try {

				System.out.println("MultiFileResourcePartitioner.partition()- " + Base64.isBase64(file.getBytes()));
				if(Base64.isBase64(file.getBytes())){
					
					context.put("fileResource", Base64.decodeBase64(file.getBytes()));
				}else{
					
					context.put("fileResource", file.getBytes());
				}

				context.put("fileReader", file.getOriginalFilename());
				context.put("fileName", file.getOriginalFilename());
				partitionMap.put(file.getOriginalFilename(), context);
			} catch (IOException e) {

				e.printStackTrace();
			}

		}
		return partitionMap;
	}

}