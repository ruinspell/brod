package ctm.platform.performance.utils;

import java.util.Random;

public class RandUtils {

	private static final Random rand = new Random();
	
	/**
	 * 
	 * @param max
	 * @return
	 */
	public static int getRandomIndex(int max) {
		return getRandomIndex(0, max);
	}
	
	/**
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static int getRandomIndex(int min, int max) {
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
	 */
	private RandUtils() {
		super();
	}
}
