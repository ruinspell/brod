package com.comparethemarket.platform.performance.utils;

import java.util.Random;

public class LoremIpsumDelegate {

	private static final String LOREM_IPSUM = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin euismod interdum risus ac tristique. Sed bibendum a elit nec auctor. Integer dictum tempor pretium. Donec in nulla suscipit, elementum purus id, lacinia ante. Quisque vulputate nunc vitae efficitur mattis. Ut semper malesuada tellus, ac suscipit quam scelerisque eget. Nam convallis elit vel risus porttitor luctus. Integer rhoncus, ante et ornare consequat, lacus elit dapibus est, sit amet aliquam dolor quam a orci. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Duis in efficitur nunc, suscipit pellentesque tellus. Nulla et vestibulum tellus. Nunc porttitor quis ex ut suscipit. Aliquam erat volutpat. Cras convallis risus sapien, sit amet tincidunt urna condimentum id. Pellentesque pellentesque ligula non vestibulum mattis. Praesent auctor rutrum neque at rhoncus. Sed aliquet augue in pharetra bibendum. Nunc sit amet viverra ante, et pretium lectus. Aenean nec magna tempus ligula dapibus venenatis ac amet.";
	private static final int LEN_LOREM_IPSUM = LOREM_IPSUM.length();
	private static final int MIN_SIZE = LEN_LOREM_IPSUM / 10;
	
	private static final Random rand = new Random();
	
	/**
	 * 
	 * @param max
	 * @return
	 */
	public static int getRandomKeyIndex(int max) {
		return getRandomKeyIndex(0, max);
	}
	
	/**
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static int getRandomKeyIndex(int min, int max) {
		int a;
		boolean ok;
		do {
			a = Math.abs(rand.nextInt(max));
			ok = ((a >= min) && (a < max));
		} while (!ok);
		return a;
	}

	/**
	 * 
	 * @return
	 */
	public static String getRandomString() {
		int a, b;
		boolean ok;
		do {
			a = getRandomKeyIndex(LEN_LOREM_IPSUM);
			b = getRandomKeyIndex(LEN_LOREM_IPSUM);
			ok = (Math.abs(a - b) >= MIN_SIZE);
		} while (!ok);
		if (a > b) {
			return LOREM_IPSUM.substring(b, a);
		}
		return LOREM_IPSUM.substring(a, b);
	}
	
	private LoremIpsumDelegate() {
		// preventing instantiation
	}
}
