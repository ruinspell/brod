package ctm.platform.performance.payload;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import org.apache.kafka.clients.producer.ProducerRecord;

import ctm.platform.performance.EventTemplate;
import ctm.platform.performance.EventTemplateFactory;
import ctm.platform.performance.template.eventsink.EventSinkEventTemplateTokens;
import ctm.platform.performance.utils.DateUtils;
import ctm.platform.performance.utils.LoremIpsumDelegate;
import ctm.platform.performance.utils.RandUtils;

public class SingleTopicNoKeyPayloadDelegate extends AbstractPayloadDelegate {

	protected final String TOPIC_NAME = "topicName";
	protected final String EVENT_TEMPLATE = "eventTemplate";

	private final String topicName; 
	private final EventTemplate eventTemplate;
		
	public SingleTopicNoKeyPayloadDelegate(Properties props) {
		
		super(props);
		
		if (!this.props.containsKey(TOPIC_NAME)) {
			throw new IllegalArgumentException();
		}

		this.topicName = (String)this.props.remove(TOPIC_NAME);

		if (!this.props.containsKey(EVENT_TEMPLATE)) {
			throw new IllegalArgumentException();
		}

		String eventName = (String)this.props.remove(EVENT_TEMPLATE);
		
		if (!"EventSink".equalsIgnoreCase(eventName)) {
			throw new IllegalArgumentException("unknown event");
		}
		
		EventTemplateFactory factory = 
				EventTemplateFactory.getFactory(eventName);
		
		this.eventTemplate = factory.createEventTemplate(); 
	}

	private Map<String,String> tokenValues = null;

	private void generateTokenValues() {
		
		this.tokenValues = new HashMap<>();
		
		tokenValues.put(
				EventSinkEventTemplateTokens.CORRELATION_GUID.toString(), 
				UUID.randomUUID().toString());
		
		tokenValues.put(
				EventSinkEventTemplateTokens.FIELD_ONE.toString(), 
				LoremIpsumDelegate.getRandomString());
		
		tokenValues.put(
				EventSinkEventTemplateTokens.FIELD_TWO.toString(), 
				LoremIpsumDelegate.getRandomString());
		
		tokenValues.put(
				EventSinkEventTemplateTokens.STREAM_GUID.toString(), 
				UUID.randomUUID().toString());

		tokenValues.put(
				EventSinkEventTemplateTokens.TIMESTAMP.toString(), 
				DateUtils.getTimestamp());
	}
	
	@Override
	protected void generatePayload() {
		generateTokenValues();
		this.payload = this.eventTemplate.getContent(tokenValues).getBytes();
	}

	@Override
	public ProducerRecord<byte[], byte[]> getRecord() {
		
		if (null == this.payload) {
			generatePayload();
		}
        return new ProducerRecord<byte[], byte[]>(topicName, this.payload);
	}
}
