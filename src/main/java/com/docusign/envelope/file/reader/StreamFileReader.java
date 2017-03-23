/**
 * 
 */
package com.docusign.envelope.file.reader;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.batch.item.ReaderNotOpenException;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.NonTransientFlatFileException;
import org.springframework.batch.item.file.separator.RecordSeparatorPolicy;
import org.springframework.batch.item.file.separator.SimpleRecordSeparatorPolicy;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.util.StringUtils;

import com.docusign.envelope.file.domain.EnvelopeDetails;

/**
 * @author Amit.Bist
 *
 */
public class StreamFileReader extends AbstractItemCountingItemStreamItemReader<EnvelopeDetails> {

	private int lineCount = 0;

	private boolean noInput = false;

	private byte[] resourceFile;

	private BufferedReader reader;
	
	private String[] comments = new String[] { "#" };

	private RecordSeparatorPolicy recordSeparatorPolicy = new SimpleRecordSeparatorPolicy();

	public RecordSeparatorPolicy getRecordSeparatorPolicy() {
		return recordSeparatorPolicy;
	}

	public void setRecordSeparatorPolicy(RecordSeparatorPolicy recordSeparatorPolicy) {
		this.recordSeparatorPolicy = recordSeparatorPolicy;
	}

	public byte[] getResourceFile() {
		return resourceFile;
	}

	public void setResourceFile(byte[] resourceFile) {
		this.resourceFile = resourceFile;
	}

	private LineMapper<EnvelopeDetails> lineMapper;

	public LineMapper<EnvelopeDetails> getLineMapper() {
		return lineMapper;
	}

	public void setLineMapper(LineMapper<EnvelopeDetails> lineMapper) {
		this.lineMapper = lineMapper;
	}

	@Override
	protected EnvelopeDetails doRead() throws Exception {
		if (noInput) {
			return null;
		}

		String line = readLine();

		if (line == null) {
			return null;
		} else {
			try {
				
				System.out.println("StreamFileReader.doRead()- " + line);
				return lineMapper.mapLine(line, lineCount);
			} catch (Exception ex) {
				
				ex.printStackTrace();
				throw new FlatFileParseException(
						"Parsing error at line: " + lineCount + " in resource=[" + "], input=[" + line + "]", ex, line,
						lineCount);
			}
		}
	}

	private String readLine() {

		System.out.println("StreamFileReader.readLine()");
		if (reader == null) {
			throw new ReaderNotOpenException("Reader must be open before it can be read.");
		}

		String line = null;

		try {
			line = this.reader.readLine();
			if (line == null) {
				return null;
			}
			lineCount++;
			
			while (isComment(line)) {
				line = reader.readLine();
				if (line == null) {
					return null;
				}
				lineCount++;
			}

			line = applyRecordSeparatorPolicy(line);
		} catch (IOException e) {
			// Prevent IOException from recurring indefinitely
			// if client keeps catching and re-calling
			noInput = true;
			throw new NonTransientFlatFileException("Unable to read from resource: [" + "]", e, line, lineCount);
		}
		
		System.out.println("line in StreamFileReader.readLine()- " + line);
		return line;
	}
	
	private boolean isComment(String line) {
		for (String prefix : comments) {
			if (line.startsWith(prefix)) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected void doOpen() throws Exception {

		InputStream is = null;
		// BufferedReader bfReader = null;
		try {
			is = new ByteArrayInputStream(resourceFile);
			reader = new BufferedReader(new InputStreamReader(is));
			
			for (int i = 0; i < 1; i++) {
				String line = readLine();
			}
			/*
			 * String temp = null; while((temp = bfReader.readLine()) != null){
			 * System.out.println(temp); }
			 */
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (Exception ex) {

			}
		}
	}

	@Override
	protected void doClose() throws Exception {
		lineCount = 0;
		if (reader != null) {
			reader.close();
		}

	}

	private String applyRecordSeparatorPolicy(String line) throws IOException {

		String record = line;
		while (line != null && !recordSeparatorPolicy.isEndOfRecord(record)) {
			line = this.reader.readLine();
			if (line == null) {
				if (StringUtils.hasText(record)) {
					// A record was partially complete since it hasn't ended but
					// the line is null
					throw new FlatFileParseException("Unexpected end of file before record complete", record,
							lineCount);
				} else {
					// Record has no text but it might still be post processed
					// to something (skipping preProcess since that was already
					// done)
					break;
				}
			} else {
				lineCount++;
			}
			record = recordSeparatorPolicy.preProcess(record) + line;
		}

		return recordSeparatorPolicy.postProcess(record);

	}

}
