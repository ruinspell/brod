package com.comparethemarket.platform.performance.event.sink;

import java.util.Map;
import java.util.regex.Pattern;

import com.comparethemarket.platform.performance.core.BaseEventTemplate;
import com.comparethemarket.platform.performance.core.EventTemplate;

public class EventSinkEventTemplate extends BaseEventTemplate {

	private EventSinkEventTemplate(final Map<String, Pattern> patterns, 
			final String templateText) {
		super(patterns, templateText);
	}

	static EventTemplate getEventTemplate(final Map<String, Pattern> patterns, 
			final String templateText) {
		return new EventSinkEventTemplate(patterns, templateText); 
	}
}
