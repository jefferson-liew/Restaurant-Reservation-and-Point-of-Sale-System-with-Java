package oodp.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import oodp.modules.MenuModule;
import oodp.modules.OrderModule;
import oodp.modules.PromotionModule;
import oodp.modules.ReportModule;
import oodp.modules.ReservationModule;

/**
 * IOHelper class that manages most input/output of the program from menu navigation to primitive user input prompting
 */
public class IOHelper {
	/**
	* Logger
	*/
	private static Logger logger = LoggerFactory.getLogger(IOHelper.class);
	/**
	* Scanner singleton
	*/
	public static Scanner sc = new Scanner(System.in);
	/**
	* The program exits if poll is ever set to false
	*/
	public static boolean poll = true;
	/**
	* The current menu page to be displayed
	*/
	public static int menu_state = 0;

	/**
	 * Initializes the IOHelper for usage This is the main application loop Scanner
	 * is set to use newline as separator
	 * 
	 * There is intentionally no option to exit the application as it does not make
	 * sense for a point-of-sale (POS) system to be manually exited
	 */
	public static void init() {
		sc.useDelimiter(System.lineSeparator());
		printGreeting();
		int inputVal = -1;
		while (poll) {
			switch (menu_state) {
			case 0:
				while (inputVal == -1) {
					printMainOptions();
					inputVal = getInt(1, 5);
					System.out.println();
					menu_state = inputVal;
				}
				inputVal = -1;
				break;
			case 1:
				while (inputVal == -1) {
					printMenuOptions();
					inputVal = getInt(1, 6, true);
					System.out.println();
					new MenuModule().process(inputVal);
				}
				inputVal = -1;
				break;
			case 2:
				while (inputVal == -1) {
					printPromotionOptions();
					inputVal = getInt(1, 5, true);
					System.out.println();
					new PromotionModule().process(inputVal);
				}
				inputVal = -1;
				break;
			case 3:
				while (inputVal == -1) {
					printOrderOptions();
					inputVal = getInt(1, 5, true);
					System.out.println();
					new OrderModule().process(inputVal);
				}
				inputVal = -1;
				break;
			case 4:
				while (inputVal == -1) {
					printReservationsOptions();
					inputVal = getInt(1, 5, true);
					System.out.println();
					new ReservationModule().process(inputVal);
				}
				inputVal = -1;
				break;
			case 5:
				while (inputVal == -1) {
					printOtherOptions();
					inputVal = getInt(1, 3, true);
					new ReportModule().process(inputVal);
				}
				inputVal = -1;
				break;
			default:
				inputVal = -1;
				break;
			}

		}
	}

	/**
	 * Sets the current menu back to the main menu
	 */
	public static void reset() {
		menu_state = 0;
	}

	/**
	 * Prints the main options (options to the different modules)
	 */
	static void printMainOptions() {
		System.out.println("1) - Menu");
		System.out.println("2) - Promotions");
		System.out.println("3) - Orders");
		System.out.println("4) - Reservations");
		System.out.println("5) - Other Management");
		System.out.print("Please enter a number from 1 to 5: ");
	}

	/**
	 * Prints the options for menu module
	 */
	static void printMenuOptions() {
		System.out.println("1) - View Full Menu");
		System.out.println("2) - View Menu Item");
		System.out.println("3) - Create Menu Item");
		System.out.println("4) - Update Menu Item");
		System.out.println("5) - Delete Menu Item");
		System.out.println("6) - Go Back");
		System.out.print("Please enter a number from 1 to 6: ");
	}

	/**
	 * Prints the options for promotion module
	 */
	static void printPromotionOptions() {
		System.out.println("1) - View Promotion Sets");
		System.out.println("2) - Create Promotion");
		System.out.println("3) - Update Promotion");
		System.out.println("4) - Delete Promotion");
		System.out.println("5) - Go Back");
		System.out.print("Please enter a number from 1 to 5: ");
	}

	/**
	 * Prints the options for order module
	 */
	static void printOrderOptions() {
		System.out.println("1) - Create Order");
		System.out.println("2) - Update Order");
		System.out.println("3) - Delete Order");
		System.out.println("4) - Print Order Invoice");
		System.out.println("5) - Go Back");
		System.out.print("Please enter a number from 1 to 5: ");
	}

	/**
	 * Prints the options for reservation module
	 */
	static void printReservationsOptions() {
		System.out.println("1) - Check Reservation");
		System.out.println("2) - Check Table Availability");
		System.out.println("3) - Create Reservation");
		System.out.println("4) - Delete Reservation");
		System.out.println("5) - Go Back");
		System.out.print("Please enter a number from 1 to 5: ");
	}

	/**
	 * Prints miscellaneous options
	 */
	static void printOtherOptions() {
		System.out.println("1) - Generate report by day");
		System.out.println("2) - Generate report by month");
		System.out.println("3) - Go Back");
		System.out.print("Please enter a number from 1 to 3: ");
	}

	/**
	 * Prints an arbitrary greeting message
	 */
	static void printGreeting() {
		System.out.println("Welcome to RRPSS V1.0!");
	}

	/**
	 * Managed method for getting an integer input from user input (range
	 * -2147483647 to 21474863647)
	 * 
	 * @return The integer value that the user has entered
	 */
	public static int getInt() {
		return getInt(-Integer.MAX_VALUE, Integer.MAX_VALUE, false, 0);
	}

	/**
	 * Managed method for getting an integer input from user (range defined by
	 * min,max)
	 * 
	 * @param min The minimum (inclusive) value that the user can enter
	 * @param max The maximum (exclusive) value that the user can enter
	 * @return The integer value that the user has entered
	 */
	public static int getInt(int min, int max) {
		return getInt(min, max, false, 0);
	}

	/**
	 * Managed method for choice selection and menu navigation. If the user inputs a
	 * value that is equals to the maximum value, the menu exits to the main screen
	 * 
	 * @param min      The minimum (inclusive) value that the user can enter
	 * @param max      The maximum (exclusive) value that the user can enter
	 * @param maxExits If true and the user enters a value that is equal to value of
	 *                 max, the menu exits
	 * @return The integer value that the user has entered
	 */
	public static int getInt(int min, int max, boolean maxExits) {
		return getInt(min, max, maxExits, 0);
	}

	/**
	 * Managed method for choice selection and menu navigation
	 * 
	 * @param min      The minimum (inclusive) value that the user can enter
	 * @param max      The maximum (exclusive) value that the user can enter
	 * @param maxExits If true and the user enters a value that is equal to value of
	 *                 max, the menu exits
	 * @param exitsTo  Main menu = 0, MenuModule = 1, PromotionModule = 2,
	 *                 OrderModule = 3, ReservationModule = 4, Misc = 5
	 * @return The integer value that the user has entered
	 */
	public static int getInt(int min, int max, boolean maxExits, int exitsTo) {
		int retVal = -1;
		boolean error = false;
		try {
			retVal = sc.nextInt();
		} catch (Exception e) {
			// logger.error(e.getMessage());
			System.out.printf("[INVALID INPUT] Please enter a number from %d to %d\n", min, max);
			sc.next(); // Invalidates input
			retVal = -1;
			error = true;
		}
		if (maxExits && retVal == max)
			menu_state = exitsTo;

		if (min != -Integer.MAX_VALUE && max != Integer.MAX_VALUE) // Checks if we are allowing the full range of
																	// integer input
			if (retVal >= min && retVal <= max) {
				return retVal;
			} else {
				if (!error)
					System.out.printf("[INVALID INPUT] Please enter a number from %d to %d\n", min, max);
				return -1;
			}
		return retVal;
	}

	/**
	 * Managed method for getting a float value from user input (range -3.4028235E38
	 * to 3.4028235E38)
	 * 
	 * @return The float value that the user has entered
	 */
	public static float getFloat() {
		return getFloat(-Float.MAX_VALUE, Float.MAX_VALUE);
	}

	/**
	 * Managed method for getting a float value from user input (range min,max)
	 * 
	 * @param min The minimum (inclusive) value that the user can enter
	 * @param max The maximum (exclusive) value that the user can enter
	 * 
	 * @return The float value that the user has entered
	 */
	public static float getFloat(float min, float max) {
		float retVal = -1;
		try {
			retVal = sc.nextFloat();
		} catch (Exception e) {
			// logger.error(e.getMessage());
			// System.out.printf("[INVALID INPUT] Please enter a number from %f to %f\n",
			// min, max);
			sc.next(); // Invalidates input
		}

		if (min != -Float.MAX_VALUE && max != Float.MAX_VALUE)
			if (retVal >= min && retVal <= max) {
				return retVal;
			} else {
				System.out.printf("[INVALID INPUT] Please enter a number from %f to %f\n", min, max);
				return -1;
			}
		return retVal;
	}

	/**
	 * Managed method for getting string from user input without a predefined length
	 * requirement The user is not repeatedly prompted for input in this overload.
	 * 
	 * @param promptMessage Message to prompt user for input
	 * @param errorMessage  Error message to show if there was an exception
	 *                      (excluding min and max length violation)
	 * @return The string value that the user has entered
	 */
	public static String getString(String promptMessage, String errorMessage) {
		return getString(null, null, false, promptMessage, errorMessage);
	}

	/**
	 * Managed method for getting string from user input without a predefined length
	 * requirement
	 * 
	 * @param repeatUntilSuccess If set to true, IO will keep prompting until a
	 *                           successful value is provided
	 * @param promptMessage      Message to prompt user for input
	 * @param errorMessage       Error message to show if there was an exception
	 *                           (excluding min and max length violation)
	 * @return The string value that the user has entered
	 */
	public static String getString(boolean repeatUntilSuccess, String promptMessage, String errorMessage) {
		return getString(null, null, repeatUntilSuccess, promptMessage, errorMessage);
	}

	/**
	 * Managed method for getting string from user input
	 * 
	 * @param minLength          The minimum length (inclusive) of the string that
	 *                           the user is expected to type
	 * @param maxLength          The maximum length (exclusive) of the string that
	 *                           the user is expected to type
	 * @param repeatUntilSuccess If set to true, IO will keep prompting until a
	 *                           successful value is provided
	 * @param promptMessage      Message to prompt user for input
	 * @param errorMessage       Error message to show if there was an exception
	 *                           (excluding min and max length violation)
	 * @return The string value that the user has entered
	 */
	public static String getString(Integer minLength, Integer maxLength, boolean repeatUntilSuccess,
			String promptMessage, String errorMessage) {
		String retVal = "";
		boolean error = false;
		while (repeatUntilSuccess) {
			try {
				error = false;
				System.out.print(promptMessage);
				retVal = sc.next();
			} catch (Exception e) {
				// logger.error(e.getMessage());
				error = true;
				System.out.println(errorMessage);
				sc.next(); // Invalidates input
			}
			if (minLength != null && (minLength >= 0 && retVal.length() < minLength)) {
				System.out.printf("[INVALID INPUT] Please enter a string with a minimum length of %d\n", minLength);
				error = true;
			} else if (maxLength != null && (maxLength > 0 && retVal.length() >= maxLength)) {
				System.out.printf("[INVALID INPUT] Please enter a string with a maximum length of %d\n", maxLength);
				error = true;
			}

			if (error && !repeatUntilSuccess)
				return "";
			if (!error)
				break;
			retVal = "";
		}
		return retVal;
	}

	/**
	 * Managed method for getting date and time BEFORE a threshold from IO
	 * 
	 * @param dtThreshold        The date object for input comparison
	 * @param format             The expected date time input format
	 * @param repeatUntilSuccess If set to true, IO will keep prompting until a
	 *                           successful value is provided
	 * @param promptMessage      Message to prompt user for input
	 * @param errorMessage       Error message to show if there was an exception
	 * @return The date value that the user has entered
	 */
	public static Date getDateTimeBefore(Date dtThreshold, String format, boolean repeatUntilSuccess,
			String promptMessage, String errorMessage) {
		Date dateTime = null;
		DateFormat formatter = new SimpleDateFormat(format);
		while (dateTime == null) {
			dateTime = getDateTime(format, repeatUntilSuccess, promptMessage, errorMessage);
			if (dateTime.after(dtThreshold)) {
				dateTime = null;
				System.out.println("The date and time you've entered must be BEFORE " + formatter.format(dtThreshold));
			}
			if (!repeatUntilSuccess)
				return dateTime;
		}
		return dateTime;
	}

	/**
	 * Managed method for getting date and time AFTER a threshold from IO
	 * 
	 * @param dtThreshold        The date object for input comparison
	 * @param format             The expected date time input format
	 * @param repeatUntilSuccess If set to true, IO will keep prompting until a
	 *                           successful value is provided
	 * @param promptMessage      Message to prompt user for input
	 * @param errorMessage       Error message to show if there was an exception
	 * @return The date value that the user has entered
	 */
	public static Date getDateTimeAfter(Date dtThreshold, String format, boolean repeatUntilSuccess,
			String promptMessage, String errorMessage) {
		Date dateTime = null;
		DateFormat formatter = new SimpleDateFormat(format);
		while (dateTime == null) {
			dateTime = getDateTime(format, repeatUntilSuccess, promptMessage, errorMessage);
			if (dateTime.before(dtThreshold)) {
				dateTime = null;
				System.out.println("The date and time you've entered must be AFTER " + formatter.format(dtThreshold));
			}
			if (!repeatUntilSuccess)
				return dateTime;
		}
		return dateTime;
	}

	/**
	 * Managed method for getting date and time from IO
	 * 
	 * @param format             The expected date time input format
	 * @param repeatUntilSuccess If set to true, IO will keep prompting until a
	 *                           successful value is provided
	 * @param promptMessage      Message to prompt user for input
	 * @param errorMessage       Error message to show if there was an exception
	 * @return The date value that the user has entered
	 */
	public static Date getDateTime(String format, boolean repeatUntilSuccess, String promptMessage,
			String errorMessage) {
		DateFormat formatter = new SimpleDateFormat(format);
		formatter.setLenient(false);
		Date dateTime = null;
		while (dateTime == null) {
			System.out.print(promptMessage);
			try {
				dateTime = formatter.parse(sc.next());

			} catch (ParseException e) {
				// logger.error(e.getMessage());
				System.out.println(errorMessage);
				if (!repeatUntilSuccess)
					return null;
			}
		}
		return dateTime;
	}
}
