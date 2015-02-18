package ctm.platform.performance.utils;

public class LoremIpsumDelegate {

	private static final String LOREM_IPSUM = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin euismod interdum risus ac tristique. Sed bibendum a elit nec auctor. Integer dictum tempor pretium. Donec in nulla suscipit, elementum purus id, lacinia ante. Quisque vulputate nunc vitae efficitur mattis. Ut semper malesuada tellus, ac suscipit quam scelerisque eget. Nam convallis elit vel risus porttitor luctus. Integer rhoncus, ante et ornare consequat, lacus elit dapibus est, sit amet aliquam dolor quam a orci. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Duis in efficitur nunc, suscipit pellentesque tellus. Nulla et vestibulum tellus. Nunc porttitor quis ex ut suscipit. Aliquam erat volutpat. Cras convallis risus sapien, sit amet tincidunt urna condimentum id. Pellentesque pellentesque ligula non vestibulum mattis. Praesent auctor rutrum neque at rhoncus. Sed aliquet augue in pharetra bibendum. Nunc sit amet viverra ante, et pretium lectus. Aenean nec magna tempus ligula dapibus venenatis ac amet.";
	private static final int LEN_LOREM_IPSUM = LOREM_IPSUM.length();
	private static final int MIN_SIZE = LEN_LOREM_IPSUM / 10;

	/**
	 * 
	 * @return
	 */
	public static String getRandomString() {
		int a, b;
		boolean ok;
		do {
			a = RandUtils.getRandomIndex(LEN_LOREM_IPSUM);
			b = RandUtils.getRandomIndex(LEN_LOREM_IPSUM);
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
