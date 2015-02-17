package com.comparethemarket.platform.performance.event.sink;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.comparethemarket.platform.performance.core.EventTemplate;
import com.comparethemarket.platform.performance.core.EventTemplateFactory;

public final class EventSinkEventFactory extends EventTemplateFactory {
	
	private static final String RESOURCE_NAME = "event-template.json";
	private static final String RESOURCE_ENCODING = "UTF-8";
	
	private static final Map<String, Pattern> patterns = new HashMap<>();
	
	static {
		patterns.put("STREAM_GUID", Pattern.compile("%%STREAM_GUID%%"));
		patterns.put("TIMESTAMP", Pattern.compile("%%TIMESTAMP%%"));
		patterns.put("CORRELATION_GUID", Pattern.compile("%%CORRELATION_GUID%%"));
		patterns.put("FIELD_ONE", Pattern.compile("%%FIELD_ONE%%"));
		patterns.put("FIELD_TWO", Pattern.compile("%%FIELD_TWO%%"));
	}
		
	private EventSinkEventFactory() {
		super(RESOURCE_NAME, RESOURCE_ENCODING);
	}
	
	public EventTemplate getEventTemplate() {
		return EventSinkEventTemplate.getEventTemplate(patterns, getTemplateText());
	}
}
