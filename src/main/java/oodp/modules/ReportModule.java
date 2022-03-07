package oodp.modules;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import oodp.AppConstants;
import oodp.helper.IOHelper;
import oodp.DataHandler;
import oodp.data.Invoice;
import oodp.data.OrderedItem;
/**
 * Report module, boundary class that handles all report generation related functions
 */
public class ReportModule implements IModule {
	private static Logger logger = LoggerFactory.getLogger(ReportModule.class);

	@Override
	public void process(int choice) {
		switch (choice) {
		case 1:
			// View report by day
			revenueByDay();
			break;
		case 2:
			// View report by month
			revenueByMonth();
			break;
		}
	}

	/** 
	 * returns the revenue by day and the quantity of individual items sold
	*/
	static void revenueByDay() {
		Date dateTime = IOHelper.getDateTime(AppConstants.FORMAT_DATETIME_INPUT_NO_TIME, true,
				"Please enter date and time (" + AppConstants.FORMAT_DATETIME_INPUT_NO_TIME + "): ",
				"There was an error parsing your input, please ensure you follow the provided format");
		ArrayList<Invoice> iList = DataHandler.getAllData(new Invoice());
		ArrayList<Invoice> filteredList = new ArrayList<Invoice>();
		Dictionary cnt = new Hashtable();
		float total = 0f;
		for (Invoice i : iList) {
			if (dateTime.getMonth() == i.getDate().getMonth() && dateTime.getDay() == i.getDate().getDay()
					&& dateTime.getYear() == i.getDate().getYear()) {
				filteredList.add(i);

				if (i.isMember()) total += i.getDiscountedPrice();
				else total += i.getPrice();

				for (OrderedItem item : i.getList()) {
					if (cnt.get(item.getItem()) == null)
						cnt.put(item.getItem(), 1);
					else
						cnt.put(item.getItem(), (Integer) cnt.get(item.getItem()) + 1);
				}
			}
		}
		SimpleDateFormat dtf = new SimpleDateFormat(AppConstants.FORMAT_DATETIME_INPUT_NO_TIME);
		System.out.println("Total invoice price of " + dtf.format(dateTime));
		System.out.println("$" + String.valueOf(total));
		System.out.println("Items sold");
		Enumeration keys = cnt.keys();
		while(keys.hasMoreElements()) {
			String elem = (String) keys.nextElement();
			System.out.println(elem + " : " + cnt.get(elem));
		}
	}


	/** 
	 * returns the revenue by month and the quantity of individual items sold
	*/
	static void revenueByMonth() {
		Date dateTime = IOHelper.getDateTime(AppConstants.FORMAT_DATETIME_INPUT_MONTH_YEAR, true,
				"Please enter date and time (" + AppConstants.FORMAT_DATETIME_INPUT_MONTH_YEAR + "): ",
				"There was an error parsing your input, please ensure you follow the provided format");
		ArrayList<Invoice> iList = DataHandler.getAllData(new Invoice());
		ArrayList<Invoice> filteredList = new ArrayList<Invoice>();
		Dictionary cnt = new Hashtable();
		float total = 0f;
		for (Invoice i : iList) {
			if (dateTime.getMonth() == i.getDate().getMonth() && dateTime.getYear() == i.getDate().getYear()) {
				filteredList.add(i);
				
				if (i.isMember()) total += i.getDiscountedPrice();
				else total += i.getPrice();

				for (OrderedItem item : i.getList()) {
					if (cnt.get(item.getItem()) == null)
						cnt.put(item.getItem(), 1);
					else
						cnt.put(item.getItem(), (Integer) cnt.get(item.getItem()) + 1);
				}
			}
		}
		SimpleDateFormat dtf = new SimpleDateFormat(AppConstants.FORMAT_DATETIME_INPUT_MONTH_YEAR);
		System.out.println("Total invoice price of " + dtf.format(dateTime));
		System.out.println("$" + String.valueOf(total));
		System.out.println("Items sold");
		Enumeration keys = cnt.keys();
		while(keys.hasMoreElements()) {
			String elem = (String) keys.nextElement();
			System.out.println(elem + " : " + cnt.get(elem));
		}
	}

}
