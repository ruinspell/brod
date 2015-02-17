package com.comparethemarket.platform.performance.core;

import java.util.Map;
import java.util.regex.Pattern;

public abstract class BaseEventTemplate implements EventTemplate {

	private final Map<String, Pattern> patterns;
	private final String templateText;
	
	///////////////////////////////////////////////////////////////////////////
	//                                                                       //
	// Public API                                                            //
	//                                                                       //
	///////////////////////////////////////////////////////////////////////////
	
	/**
	 * 
	 */
	public String getContent(Map<String, String> tokensValues) {
		return replaceTokens(patterns, templateText, tokensValues);
	}

	///////////////////////////////////////////////////////////////////////////
	//                                                                       //
	// Protected API                                                         //
	//                                                                       //
	///////////////////////////////////////////////////////////////////////////
	
	protected BaseEventTemplate(final Map<String, Pattern> patterns, 
			final String templateText) {
		this.patterns = patterns; 
		this.templateText = templateText;
	}

	///////////////////////////////////////////////////////////////////////////
	//                                                                       //
	// Static Methods                                                        //
	//                                                                       //
	///////////////////////////////////////////////////////////////////////////

	/**
	 * Applies the regex patterns and given values to a given template 
	 * string. This method replaces the tokens matching the patterns with 
	 * the associated value found in the Map entry. 
	 *  
	 * @param patterns A Map with compiled regex patterns.  
	 * @param templateText The template text full of tokens.  
	 * @param data A Map with token values. 
	 * @return a String will all matched patterns replaced with the 
	 * corresponding value.   
	 */
	private static String replaceTokens(Map<String, Pattern> patterns, 
			String templateText, Map<String, String> data) {
		
		String s = templateText; 
		
		for (Map.Entry<String, String> entry : data.entrySet()) {
			
			String key = entry.getKey();
			
			if (!patterns.containsKey(key)) {
				throw new IllegalArgumentException(key);
			}
			
			s = patterns.get(key).matcher(s).replaceAll(entry.getValue());
		}
		
		return s;
	}
}
