package oodp;
/**
 * Constants that are defined for the program at compilation time
 */
public class AppConstants {
	private static final int reservationDecayMinutes = 5;

	/**
	 * Set to false to prevent logger warnings about model-level validation
	 */
	public static final boolean allowLogging = false;

	/**
	 * Format for display datetime without seconds
	 */
	public static final String FORMAT_DATETIME_DISPLAY_NO_SECONDS = "dd-MMM-YYYY hh:mma '('HHmm' hrs)'";
	/**
	 * Format for datetime input without seconds
	 */
	public static final String FORMAT_DATETIME_INPUT_NO_SECONDS = "dd/MM/yyyy HHmm";
	
	/**
	 * Format for datetime input without time
	 */
	public static final String FORMAT_DATETIME_INPUT_NO_TIME = "dd/MM/yyyy";
	
	/**
	 * Format for datetime input without day and time
	 */
	public static final String FORMAT_DATETIME_INPUT_MONTH_YEAR = "MM/yyyy";
	
	/**
	 * Returns the reservation expiry/decay in milliseconds
	 * @return the decay timing in milliseconds
	 */
	public static final long getReservationDecay() {
		return reservationDecayMinutes * 60 * 1000;
	}
}
