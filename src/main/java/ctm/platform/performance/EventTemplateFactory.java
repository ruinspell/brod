package ctm.platform.performance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public abstract class EventTemplateFactory {

	private static final String SUPPORTED_FACTORIES = "supported-factories.properties";

	private final String resourceName;
	private String resourceText;

	//////////////////////////////////////////////////////////////////////////
	//
	// Constructors
	//
	///////////////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param resource
	 *            The resource file this factory will create a Template from.
	 */
	protected EventTemplateFactory(String resource) {
		this.resourceName = resource;
	}

	// /////////////////////////////////////////////////////////////////////////
	// //
	// Static Methods //
	// //
	// /////////////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param resource
	 * @param encoding
	 * @return
	 */
	protected static String readResource(ClassLoader classLoader, 
			String resource) {

		try (BufferedReader in = new BufferedReader(new InputStreamReader(
				classLoader.getResourceAsStream(resource)))) {
			
			String s;
			
			StringBuilder sb = new StringBuilder();
			
			while ((s = in.readLine()) != null) {
				sb.append(s);
			}

			return sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		throw new IllegalStateException("Resource could not be loaded");
	}

	/**
	 * 
	 * @return
	 */
	private static Properties loadFactories(ClassLoader classLoader) {

		try (BufferedReader in = new BufferedReader(new InputStreamReader(
				classLoader.getResourceAsStream(SUPPORTED_FACTORIES)))) {

			Properties factories = new Properties();
			factories.load(in);
			return factories;

		} catch (IOException e) {
			e.printStackTrace();
		}

		throw new IllegalStateException("Factories could not be loaded");
	}

	/**
	 * 
	 * @param factoryName
	 * @return
	 */
	private static String getKey(String factoryName) {
		return new StringBuilder("factory.").append(factoryName).toString();
	}

	// /////////////////////////////////////////////////////////////////////////
	// //
	// Instance Methods //
	// //
	// /////////////////////////////////////////////////////////////////////////

	/**
	 * Lazily loads the template file content and returns it as a String object.
	 * 
	 * @return Returns the template file content as a String object
	 */
	protected String getTemplateText() {

		if (null == resourceText) {
			resourceText = readResource(
					EventTemplateFactory.class.getClassLoader(), resourceName);
		}
		
		return resourceText;
	}

	// /////////////////////////////////////////////////////////////////////////
	// //
	// Public API //
	// //
	// /////////////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param factoryName
	 * @return
	 */
	public static EventTemplateFactory getFactory(String factoryName) {

		ClassLoader classLoader = 
				Thread.currentThread().getContextClassLoader();
		
		Properties factories = 
				loadFactories(classLoader);

		String key = getKey(factoryName);
		
		if (!factories.containsKey(key)) {
			throw new IllegalArgumentException(factoryName);
		}

		try {

			return (EventTemplateFactory) classLoader.loadClass(
					factories.getProperty(key)).newInstance();

		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | ClassCastException e) {
			e.printStackTrace();
		}

		throw new IllegalStateException("Factory could not be loaded");
	}

	/**
	 * 
	 * @return
	 */
	public abstract EventTemplate createEventTemplate();
}