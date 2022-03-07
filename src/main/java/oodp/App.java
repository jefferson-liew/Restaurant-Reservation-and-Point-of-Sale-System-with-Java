package oodp;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import oodp.data.Customer;
import oodp.data.MenuItem;
import oodp.data.Staff;
import oodp.data.Table;
import oodp.helper.IOHelper;
import oodp.helper.SeedHelper;

/**
 * Main App Class
 */
public class App {	
	/**
	 * Logger
	 */
	private static Logger logger = LoggerFactory.getLogger(App.class);

	/**
	 * Main method
	 * @param args Command line arguments from app invocation
	 */
	public static void main(String[] args) {
		seedAllData();
		IOHelper.init();
	}

	/**
	 * Seeds table,staff,customer and menu item data if they're empty
	 */
	public static void seedAllData() {
		seedTables();
		seedStaff();
		seedCustomer();
		seedMenuItem();
	}

	/**
	 * Seeds staff data if there are none
	 */
	private static void seedStaff() {
		ArrayList<Staff> list = DataHandler.getAllData(new Staff());
		if (list.size() == 0) {
			SeedHelper.seedDataStaff();
		}
	}
	/**
	 * Seeds customer data if there are none
	 */
	private static void seedCustomer() {
		ArrayList<Customer> list = DataHandler.getAllData(new Customer());
		if (list.size() == 0) {
			SeedHelper.seedDataCustomer();
		}
	}

	/**
	 * Seeds table data if there are none
	 */
	private static void seedTables() {
		ArrayList<Table> list = DataHandler.getAllData(new Table());
		if (list.size() == 0) {
			SeedHelper.seedDataTable();
		}
	}

	/**
	 * Seeds menu item data if there are none
	 */
	private static void seedMenuItem() {
		ArrayList<MenuItem> list = DataHandler.getAllData(new MenuItem());
		if(list.size()==0) {
			SeedHelper.seedDataMenuItem();
		}
	}

}
