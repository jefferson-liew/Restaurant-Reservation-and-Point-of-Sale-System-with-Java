package oodp.helper;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import oodp.DataHandler;
import oodp.data.Customer;
import oodp.data.MenuItem;
import oodp.data.Staff;
import oodp.data.Table;
/**
 * Helper module to seed the database for initial use
 */
public class SeedHelper {
	private static Logger logger = LoggerFactory.getLogger(SeedHelper.class);


	/**
	 * Wipes out all existing data
	 * Seeds table,staff,customer,menu item data unconditionally
	 */
	public static void seedAllData() {
		try {
			DataHandler.wipeData();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		seedDataTable();
		seedDataStaff();
		seedDataCustomer();
		seedDataMenuItem();
	}
	
	/**
	 * Seeds all menu item data
	 */
	public static void seedDataMenuItem() {
		DataHandler.saveData(new MenuItem("Cheese Bread", "bread with cheese", 5.00f, "Appetiser"));
		DataHandler.saveData(new MenuItem("Big Sausage", "6 inch sausage", 5.50f, "Mains"));
		DataHandler.saveData(new MenuItem("Giant Sausage", "10 inch sausage", 6.90f, "Mains"));
		DataHandler.saveData(new MenuItem("Cream Cheese", "it's really cheesy", 4.90f, "Dessert"));
		DataHandler.saveData(new MenuItem("Cream Puff", "it's a puff filled with cream", 4.50f, "Dessert"));
	}

	
	/**
	 * Seeds all staff data
	 */
	public static void seedDataStaff() {
		DataHandler.saveData(new Staff("EMP-001", "Mary Jane", "Female", "Janitor"));
		DataHandler.saveData(new Staff("EMP-002", "Alice Walker", "Female", "Dishwasher"));
		DataHandler.saveData(new Staff("EMP-003", "Leonardo Da Vinci", "Male", "Aesthetics Guru"));
		DataHandler.saveData(new Staff("EMP-004", "Gordon Ram Easy", "Male", "Head chef"));
		DataHandler.saveData(new Staff("EMP-005", "Uncle Roger", "Male", "Receptionist"));
	}

	
	/**
	 * Seeds all customer data
	 */
	public static void seedDataCustomer() {
		DataHandler.saveData(new Customer("Leroy Jenkins", "Male", true));
		DataHandler.saveData(new Customer("John Cena", "Male", false));
		DataHandler.saveData(new Customer("Marie Curie", "Female", true));
		DataHandler.saveData(new Customer("Jeff Bezos", "Male", true));
	}

	
	/**
	 * Seeds all table data
	 * There are 30 preset tables in total
	 * 10 with 10 capacity,
	 * 5 with 5 capacity
	 * 15 with 20 capacity
	 */
	public static void seedDataTable() {
		for (int x = 1; x <= 10; x++) {
			DataHandler.saveData(new Table(x, 10));
		}
		for (int x = 10; x <= 15; x++) {
			DataHandler.saveData(new Table(x, 5));
		}
		for (int x = 15; x <= 30; x++) {
			DataHandler.saveData(new Table(x, 20));
		}
	}
}
