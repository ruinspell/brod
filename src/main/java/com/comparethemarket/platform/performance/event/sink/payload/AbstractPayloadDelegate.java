package com.comparethemarket.platform.performance.event.sink.payload;

import java.util.Properties;

import org.apache.kafka.clients.producer.ProducerRecord;

import com.comparethemarket.platform.performance.core.PayloadDelegate;

public abstract class AbstractPayloadDelegate implements PayloadDelegate {

	protected final Properties props;
	protected byte[] payload = null;

	public AbstractPayloadDelegate(final Properties props) {
		super();
		this.props = props;
	}

	/**
	 * 
	 */
	protected abstract void generatePayload();
	
	/**
	 * 
	 */
	public byte[] nextPayload() {
		generatePayload();
	    return this.payload; 
	}
	
	public abstract ProducerRecord<byte[], byte[]> getRecord();

	/**
	 * 
	 */
	public void clearPayload() {
		this.payload = null; 
	}
}