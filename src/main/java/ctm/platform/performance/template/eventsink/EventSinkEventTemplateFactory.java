package ctm.platform.performance.template.eventsink;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import ctm.platform.performance.EventTemplate;
import ctm.platform.performance.EventTemplateFactory;

public final class EventSinkEventTemplateFactory extends EventTemplateFactory {
	
	private static final String RESOURCE_NAME = "event-template.json";
	
	private static final Map<String, Pattern> patterns = new HashMap<>();
	
	static {
		patterns.put("STREAM_GUID", Pattern.compile("%%STREAM_GUID%%"));
		patterns.put("TIMESTAMP", Pattern.compile("%%TIMESTAMP%%"));
		patterns.put("CORRELATION_GUID", Pattern.compile("%%CORRELATION_GUID%%"));
		patterns.put("FIELD_ONE", Pattern.compile("%%FIELD_ONE%%"));
		patterns.put("FIELD_TWO", Pattern.compile("%%FIELD_TWO%%"));
	}
		
	public EventSinkEventTemplateFactory() {
		super(RESOURCE_NAME);
	}
	
	/**
	 * 
	 */
	@Override
	public EventTemplate createEventTemplate() {
		return EventSinkEventTemplate.getEventTemplate(patterns, getTemplateText());
	}
}
