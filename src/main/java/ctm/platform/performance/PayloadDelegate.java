package ctm.platform.performance;

import org.apache.kafka.clients.producer.ProducerRecord;

public interface PayloadDelegate {
	
	/**
	 * 
	 * @return
	 */
	byte[] nextPayload();

	/**
	 * 
	 * @return
	 */
	ProducerRecord<byte[], byte[]> getRecord();
	
	
	void clearPayload();
        
        void dismiss();

}
