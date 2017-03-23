/**
 * 
 */
package com.docusign.envelope.batch.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.annotation.OnSkipInProcess;
import org.springframework.batch.core.annotation.OnSkipInRead;
import org.springframework.batch.core.annotation.OnSkipInWrite;
import org.springframework.batch.core.listener.SkipListenerSupport;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.item.file.FlatFileParseException;

import com.docusign.envelope.file.domain.EnvelopeDetails;

/**
 * @author Amit.Bist
 *
 */
public class DataRecordSkipHandler extends SkipListenerSupport<EnvelopeDetails, EnvelopeDetails> {

	private static final Logger logger = LoggerFactory.getLogger(DataRecordSkipHandler.class);

	/*private ItemStreamWriter<Object> csvFileFailItemWriter;

	public ItemStreamWriter<Object> getCsvFileFailItemWriter() {
		return csvFileFailItemWriter;
	}

	public void setCsvFileFailItemWriter(ItemStreamWriter<Object> csvFileFailItemWriter) {
		this.csvFileFailItemWriter = csvFileFailItemWriter;
	}*/

	@OnSkipInRead
	public void onSkipInRead(Throwable t) {
		if (t instanceof FlatFileParseException) {
			FlatFileParseException ffpe = (FlatFileParseException) t;
			logAndWriteSkippedItem("Reader skip:" + ffpe.getInput() + " Throwable- " + t.getMessage(), null);
		}
	}

	@OnSkipInWrite
	public void onSkipInWrite(EnvelopeDetails item, Throwable t) {
		logAndWriteSkippedItem("Writer skip: " + item + " Throwable- " + t.getMessage(), item);
	}

	@OnSkipInProcess
	public void onSkipInProcess(EnvelopeDetails item, Throwable t) {

		if (null != t) {
			logAndWriteSkippedItem(
					"Processor skip: " + item + " Error message- " + t.getMessage() + " Throwable- " + t.getMessage(),
					item);
		} else {
			logAndWriteSkippedItem("Processor skip: " + item, item);
		}

	}

	public void logAndWriteSkippedItem(String messageToLog, Object item) {
		logger.info(messageToLog.toString());

		if (null != item) {
			List<Object> itemsList = new ArrayList<Object>();
			itemsList.add(item);
			try {
//				csvFileFailItemWriter.write(itemsList);
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
	}

}