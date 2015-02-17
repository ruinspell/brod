package com.comparethemarket.platform.performance.event.sink.payload;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.producer.ProducerRecord;
import com.comparethemarket.platform.performance.core.PayloadDelegate;

public class ByteArrayPayloadDelegate extends AbstractPayloadDelegate implements PayloadDelegate {
	
	private final String topicName; 
	final int recordSize; 

	/**
	 * 
	 * @param props
	 */
	public ByteArrayPayloadDelegate(final Properties props) {
		super(props);
		if (!this.props.containsKey("TOPIC_NAME")) {
			throw new IllegalArgumentException();
		}
		
		this.topicName = this.props.getProperty("TOPIC_NAME");

		if (!this.props.containsKey("RECORD_SIZE")) {
			throw new IllegalArgumentException();
		}

		this.recordSize = 
				Integer.parseInt(this.props.getProperty("RECORD_SIZE"));
	}

	/**
	 * 
	 */
	protected void generatePayload() {
	    this.payload = new byte[this.recordSize];
	    Arrays.fill(this.payload, (byte) 1);
	}

	/**
	 * 
	 */
	public ProducerRecord<byte[], byte[]> getRecord() {
		if (null == this.payload) {
			generatePayload();
		}
        return new ProducerRecord<byte[], byte[]>(topicName, this.payload);
	}	
}
