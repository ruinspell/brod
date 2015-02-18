package ctm.platform.performance.template.eventsink;

import java.util.Map;
import java.util.regex.Pattern;

import ctm.platform.performance.EventTemplate;
import ctm.platform.performance.template.BaseEventTemplate;

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
