package JMSCAPI.misc;

/**
 * KP_PADDING:
 * <ul>
 * <li>{@linkplain #PKCS5_PADDING}</li>
 * <li>{@linkplain #RANDOM_PADDING}</li>
 * <li>{@linkplain #ZERO_PADDING}</li>
 * </ul>
 */
public interface KP_PADDING {

	/**
	 * Specifies the PKCS 5 (sec 6.2) padding method.
	 */
	public static final int PKCS5_PADDING      =    1;       // PKCS 5 (sec 6.2) padding method
	
	/**
	 * The padding uses random numbers. This padding method is not supported by the Microsoft
	 * supplied CSPs.
	 */
	public static final int RANDOM_PADDING     =    2;
	
	/**
	 * The padding uses zeros. This padding method is not supported by the Microsoft supplied CSPs.
	 */
	public static final int ZERO_PADDING       =    3;
}
