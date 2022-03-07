package oodp.data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.print.attribute.standard.DateTimeAtCreation;

import org.apache.logging.log4j.core.config.Order;

import oodp.AppConstants;
import oodp.data.base.BaseData;
/** *
 * Represents an invoice
 * 
 */

public class Invoice extends BaseData{
	/** *
	 * The date of the invoice
	 */
	Date date;
	/** *
	 * The price of the order
	 */
	float price;
	/** *
	 * The list of items in the order
	 */
	ArrayList<OrderedItem> list;
	/** *
	 * The ID of the staff that invoiced order
	 */
	String staffID;
	/** *
	 * The table number of the order
	 */
    int tableNumber;
	/** *
	 * Customer is a member
	 */
	boolean hasMembership;
 
	/**
	 * Empty constructor for DataHandler to access class name
	 */
	public Invoice() {
		super();
	}
    
    /** *
     * The details of the invoice
     * @param staffID	The ID of the staff that invoiced order
     * @param tableNumber	The table number of the order
     * @param list	The list of items in the order
     * @param price	The price of the order
	 * @param hasMembership Customer is a member
     */
	public Invoice(String staffID, int tableNumber, ArrayList<OrderedItem> list, float price, boolean hasMembership) {
		// this.date = DateTimeAtCreation();
		this.staffID = staffID;
		this.tableNumber = tableNumber;
		this.list = list;
		this.price = price;
		this.hasMembership = hasMembership;
		this.date = new Date();
	}
	
	/** *
	 * Gets the date of the invoice as a string
	 * @return this invoice's date
	 */
	public String getDateString() {
		SimpleDateFormat dtf = new SimpleDateFormat(AppConstants.FORMAT_DATETIME_DISPLAY_NO_SECONDS);
		return dtf.format(this.date);
	}
	/**
	 * Gets the date of the invoice in date format
	 * @return this invoice's date
	 */
	public Date getDate() {
		return this.date;
	}
	/**
	 * 
	 * @return membership status of customer
	 */
	public boolean isMember() {
		return this.hasMembership;
	}
	/** *
	 * Gets the id of the staff that took the order
	 * @return this invoice's staffID
	 */
	public String getStaffID() {
		return this.staffID;
	}
	/** *
	 * Gets the table number of the invoice
	 * @return this invoice's table number
	 */
	public int getTableNum() {
		return this.tableNumber;
	}
	/** *
	 * Gets the price of the invoice
	 * @return this invoice's price
	 */
	public float getPrice() {
		return this.price;
	}

	/**
	 * Gets the discounted price of the invoice
	 * @return this invoice's discounted price
	 */
	public float getDiscountedPrice() {
		return (0.8f * this.price);
	}
	/** *
	 * Gets the list of the items ordered
	 * @return this invoice's list of items ordered
	 */
	public ArrayList<OrderedItem> getList(){
		return this.list;
	}
}