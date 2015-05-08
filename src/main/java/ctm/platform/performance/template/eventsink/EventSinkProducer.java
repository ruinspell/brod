/**
 * 
 */
package ctm.platform.performance.template.eventsink;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import ctm.platform.performance.PayloadDelegate;
import ctm.platform.performance.payload.ByteArrayPayloadDelegate;
import ctm.platform.performance.payload.ByteArrayWithKeyPayloadDelegate;
import ctm.platform.performance.payload.SingleTopicNoKeyPayloadDelegate;
import ctm.platform.performance.payload.UUIDAsTopicNoKeyPayloadDelegate;

/**
 * @author fabiool
 *
 */
public class EventSinkProducer {
	
	private final String scenario;
	private final Properties props;
	private final KafkaProducer<byte[], byte[]> producer; 
	private final PayloadDelegate payloadDelegate;
	
	private PayloadDelegate getPayloadDelegate() {

		if ("ByteArray".equalsIgnoreCase(this.scenario)) {
			
			return new ByteArrayPayloadDelegate(this.props);
		}
                
		if ("ByteArrayWithKey".equalsIgnoreCase(this.scenario)) {
			
			return new ByteArrayWithKeyPayloadDelegate(this.props);
		}
        
		if ("UUIDAsTopicNoKey".equalsIgnoreCase(this.scenario)) {
			
			return new UUIDAsTopicNoKeyPayloadDelegate(this.props);
		}

		if ("SingleTopicNoKey".equalsIgnoreCase(this.scenario)) {
			
			return new SingleTopicNoKeyPayloadDelegate(this.props);
		}

		throw new IllegalArgumentException("scenario");
	}
	
	public EventSinkProducer(final String scenario, final Properties props) {
		this.scenario = scenario;
		this.props = props;
		this.payloadDelegate = getPayloadDelegate();
		this.producer = new KafkaProducer<>(this.props);
	}
	
	public byte[] nextPayload() {
		return this.payloadDelegate.nextPayload();
	}
	
	protected ProducerRecord<byte[], byte[]> getRecord() {
		return payloadDelegate.getRecord();  
	}
	
	public void send(Callback cb) {
		this.producer.send(getRecord(), cb);
	}
	
	public void close() {
		this.producer.close();
	}
}
