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

public class UUIDAsTopicNoKeyPayloadDelegate extends AbstractPayloadDelegate {

	protected final String SESSIONS_COUNT = "sessionsCount";
	protected final String EVENT_TEMPLATE = "eventTemplate";

	private final int sessionsCount; 
	private final EventTemplate eventTemplate;
	
	private final String[] sessionIDs;
	
	private static void generateSessionIDs(int count, String[] target) {
		for(int i=0; i< count; i++) {
			target[i] = UUID.randomUUID().toString(); 
		}
	}
	
	public UUIDAsTopicNoKeyPayloadDelegate(Properties props) {
		
		super(props);
		
		if (!this.props.containsKey(SESSIONS_COUNT)) {
			throw new IllegalArgumentException();
		}

		this.sessionsCount = 
				Integer.parseInt((String)this.props.remove(SESSIONS_COUNT));

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
						
		sessionIDs = new String[this.sessionsCount];
		
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
		generateSessionIDs(this.sessionsCount, this.sessionIDs);//
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
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
				this.sessionIDs[
				    RandUtils.getRandomIndex(this.sessionIDs.length)]);

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
		
		String topicName = tokenValues.get(
				EventSinkEventTemplateTokens.STREAM_GUID.toString());
		
        return new ProducerRecord<byte[], byte[]>(topicName, this.payload);
	
	}
}
