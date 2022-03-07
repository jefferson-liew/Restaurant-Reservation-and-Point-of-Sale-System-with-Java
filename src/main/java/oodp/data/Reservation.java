package oodp.data;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import oodp.AppConstants;
import oodp.DataHandler;
import oodp.data.base.BaseData;
/**
 * Reservation
 */
public class Reservation extends BaseData {
	private static Logger logger = LoggerFactory.getLogger(Reservation.class);
	Date reservationDateTime;
	Date reservationDecayDateTime;
	int pax;
	String name;
	String contactNumber;
	String membershipId;

	boolean valid;
	Table assignedTable;

	/**
	 * Empty constructor for DataHandler to access class name
	 */
	public Reservation() {
		super();
	}

	/**
	 * Constructor for Reservation
	 * 
	 * @param reservationDateTime        Date and time of reservation
	 * @param pax             			 Pax count
	 * @param name 						 Customer name
	 * @param contactNumber              Customer contact number
	 * @param membershipId               Customer membership Id
	 */
	public Reservation(Date reservationDateTime, int pax, String name, String contactNumber, String membershipId) {
		super();
		this.reservationDateTime = reservationDateTime;
		this.reservationDecayDateTime = new Date(reservationDateTime.getTime() + AppConstants.getReservationDecay());
		this.pax = pax;
		this.name = name;
		this.contactNumber = contactNumber;
		this.membershipId = membershipId;
		this.valid = true;
	}

	/**
	 * Get pax
	 * @return Value of pax
	 */
	public int getPax() {
		return pax;
	}

	/**
	 * Get customer name
	 * @return Customer's name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Sets customer name
	 * 
	 * @param name               Name of customer
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get customer contact number
	 * @return Customer's contact number
	 */
	public String getContactNumber() {
		return contactNumber;
	}
	/**
	 * Sets customer contact number
	 * 
	 * @param contactNumber    Customer's contact number
	 */
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	/**
	 * Get customer membership Id
	 * @return Customer's membership Id
	 */
	public String getMembershipId() {
		if (membershipId == null || membershipId.equals(""))
			return "Not Applicable";
		return membershipId;
	}
	/**
	 * Get assigned table of reservation
	 * @return Assigned table
	 */
	public Table getAssignedTable() {
		return assignedTable;
	}
	/**
	 * Sets reservation assigned table
	 * 
	 * @param assignedTable    The assigned table for this reservation
	 */
	public void setAssignedTable(Table assignedTable) {
		this.assignedTable = assignedTable;
	}

	/**
	 * Returns whether the reservation is valid
	 * @return Boolean to indicate whether the reservation is valid
	 */
	public boolean isValid() {
		return valid;
	}
	
	/**
	 * Returns whether the reservation is made with a membershipId
	 * @return Boolean to indicate whether the there is a membershipId
	 */
	public boolean isMember() {
		return !membershipId.trim().equals("");
	}

	/**
	 * Overrides the validateBeforeSave() method from BaseData This allows for a
	 * 'last line of defense' data validation before it is stored in the filesystem
	 * 
	 * isValidForSaving flag must be set to true to signify that the data is valid
	 * and can be saved
	 */
	@Override
	public void validateBeforeSave() {
		String errors = "";

		if (this.name == null || this.name.trim().equals(""))
			errors += "Customer name cannot be blank\n";

		if (this.contactNumber == null || this.contactNumber.trim().equals(""))
			errors += "Customer contact number cannot be blank\n";

		if (this.pax <= 0)
			errors += "Pax count cannot be less than or equals to zero";

		if (this.assignedTable == null)
			errors += "Assigned table cannot be null";

		// Check if there were any validation failures
		if (errors.equals(""))
			isValidForSaving = true; // Set isValidForSaving = true as there are no validation failures
		else {
			if (AppConstants.allowLogging) {
				logger.error("Data from " + getClass().getName()
						+ " will not be saved due to the following validation failures");
				logger.error(errors);
			}
		}
	}

	/**
	 * Sets the valid flag to false
	 */
	public void invalidate() {
		this.valid = false;
	}
	
	/**
	 * Check whether the reservation should be invalidated.
	 * If it is past the decay datetime, the reservation is invalidated and relevant table is unreserved
	 */
	public void checkInvalidate() {
		if (valid && new Date().after(reservationDecayDateTime)) {
			this.assignedTable.unreserve();
			this.valid = false;
			DataHandler.saveData(this.assignedTable);
			DataHandler.saveData(this);
		}
	}

	/**
	 * Returns a multiline formatted string containing all key information of the reservation
	 * @return The formatted reservation information
	 */
	public String getReservationInfo() {
		SimpleDateFormat dtf = new SimpleDateFormat(AppConstants.FORMAT_DATETIME_DISPLAY_NO_SECONDS);
		return "Reservation Reference Number: " + getUUID() + "\nReservation By: " + this.name + "\nPax Count: "
				+ Integer.valueOf(this.pax) + "\nContact Number: " + this.contactNumber + "\nMembership ID: "
				+ getMembershipId() + "\nReservation Date: " + dtf.format(reservationDateTime) + "\nExpiry Date: "
				+ dtf.format(reservationDecayDateTime) + "\nTable Number: "
				+ String.valueOf(assignedTable.getTableNumber());
	}
	
	/**
	 * Overrides the toString method to return a single line string containing minimal basic key information of the reservation
	 * @return The single line of reservation information
	 */
	@Override
	public String toString() {
		SimpleDateFormat dtf = new SimpleDateFormat(AppConstants.FORMAT_DATETIME_DISPLAY_NO_SECONDS);
		return "Reservation Reference No " + getUUID() + " by " + this.name + (!membershipId.trim().equals("") ? (" (MemberId " + membershipId.trim() + ")") : "") +  ", contact number " + this.contactNumber + ", at " + dtf.format(reservationDateTime);
	}
}
