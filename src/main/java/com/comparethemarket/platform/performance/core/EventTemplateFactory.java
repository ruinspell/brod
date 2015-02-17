package com.comparethemarket.platform.performance.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public abstract class EventTemplateFactory {

	private static final String DEFAULT_ENCODING = "UTF8";

	private final File resource;
	private final String resourceEncoding;
	private String resourceText;
	
	///////////////////////////////////////////////////////////////////////////
	//                                                                       //
	// Constructors                                                          //
	//                                                                       //
	///////////////////////////////////////////////////////////////////////////
	
	/** 
	 * 
	 * @param resource The resource file this factory will create a Template 
	 *        from. 
	 */
	protected EventTemplateFactory(String resource, String encoding) {
		
		ClassLoader classLoader = getClass().getClassLoader();
		File resourceFile = new File(classLoader.getResource(resource).getFile());
		
		if (!validFile(resourceFile)) {
			throw new IllegalArgumentException("Invalid resource");
		}

		this.resource = resourceFile;
		
		this.resourceEncoding = 
				((null == encoding) || "".equals(encoding)) ? 
						DEFAULT_ENCODING : encoding; 
	}	

	///////////////////////////////////////////////////////////////////////////
	//                                                                       //
	// Static Methods                                                        //
	//                                                                       //
	///////////////////////////////////////////////////////////////////////////

	/**
	 * Utility method that reads a text file returning its content as a String 
	 * 
	 * @param templateFile The text file to read from. 
	 * @return The text file content as a String
	 * @throws RuntimeException if failed to read the file or encoding issues.
	 */
	protected static String readFile(File file, String encoding) {
		try {
			StringBuilder sb;
			try (BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), encoding))) {
				sb = new StringBuilder();
				String s;
				while ((s = in.readLine()) != null) {
					sb.append(s);
				}
			}
			return sb.toString();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e); 
		}
	}
		
	/**
	 * Validates the given file. 
	 * @param file The File object to be validated. 
	 * @return Returns true if the file exists, is an actual file and 
	 * can be read from. 
	 */
	protected static boolean validFile(File file) {
		return file.exists() && file.isFile() && file.canRead(); 
	}
	
	///////////////////////////////////////////////////////////////////////////
	//                                                                       //
	// Instance Methods                                                      //
	//                                                                       //
	///////////////////////////////////////////////////////////////////////////

    /**
	 * Lazily loads the template file content and returns it as a String object.  
	 * @return Returns the template file content as a String object
	 */
    protected String getTemplateText() {
    	if (null == resourceText) {
    		resourceText = readFile(resource, resourceEncoding);
    	}
    	return resourceText;
    }	
}