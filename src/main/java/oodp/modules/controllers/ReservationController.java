package oodp.modules.controllers;

import java.util.ArrayList;
import java.util.Comparator;

import oodp.DataHandler;
import oodp.data.Reservation;
import oodp.data.Table;
import oodp.helper.IOHelper;

/**
 * Control class between ReservationModule and Reservation Entity
 */
public class ReservationController {
	/**
	 * Assign a table to a reservation based on capacity
	 * 
	 * @param reservation The reservation object to act on
	 * @return A boolean to indicate whether the assignment was successful
	 */
	public boolean assignReservationTable(Reservation reservation) {
		ArrayList<Table> tableList = DataHandler.getAllData(new Table());
		for (Table i : tableList) {
			if (!i.isReserved() && reservation.getPax() < i.getCapacity()) {
				i.reserve();
				reservation.setAssignedTable(i);
				if (DataHandler.saveData(i))
					return true;
				else
					return false;
			}
		}
		return false;
	}

	/**
	 * Check if a table is reserved
	 * The table is retrieved using table number
	 * 
	 * @param tableNumber Table number to retrieve
	 * @return The retrieved table object
	 * @throws Exception when there is no table found
	 */
	public boolean isTableReserved(int tableNumber) throws Exception {
		ArrayList<Table> tableList = DataHandler.getAllData(new Table());
		for (Table i : tableList) {
			if (i.getTableNumber() == tableNumber) {
				if (i.isReserved())
					return true;
				else
					return false;
			}
		}
		throw new Exception("Table not found");
	}

	/**
	 * Check if there is a table available for  a specifiec pax count
	 * 
	 * @param pax The pax number to cater for
	 * @return An ArrayList consisting of all suitable tables
	 */
	public ArrayList<Table> getTableAvailabilityByPax(int pax) {
		ArrayList<Table> tableList = DataHandler.getAllData(new Table());
		ArrayList<Table> outList = new ArrayList<Table>();
		for (Table i : tableList)
			if (!i.isReserved() && i.getCapacity() >= pax)
				outList.add(i);
		outList.sort(new Comparator<Table>() { // Simple comparator anonymous function to sort by table number
			@Override
			public int compare(Table p1, Table p2) {
				return p1.getTableNumber() - p2.getTableNumber();
			}
		});
		return outList;
	}

	/**
	 * Method to loop through all reservations to check and invalidate them if necessary
	 */
	public void checkReservationInvalidate() {
		// Invalidates reservations that are past their decay time
		ArrayList<Reservation> reservationList = DataHandler.getAllData(new Reservation());
		for (Reservation i : reservationList) {
			i.checkInvalidate();
		}
	}

	/**
	 * IO method for getReservationByReference
	 * 
	 * @return the Reservation object based retrieved based on reference number
	 */
	public Reservation doGetReservationByReference() {
		Reservation reservation = null;
		String referenceNum = "";
		referenceNum = IOHelper.getString(true, "Please insert reference number: ",
				"Please ensure you are typing in a string value");
		reservation = getReservationByReference(referenceNum);
		if (reservation == null)
			System.out.println("Unable to find a reservation with reference number " + referenceNum);
		else {
			System.out.println(reservation.getReservationInfo());
		}
		return reservation;
	}
	
	/**
	 * Get reservation by reference number
	 * 
	 * @param referenceNum String The reference number of the reservation
 	 * @return the Reservation object based retrieved based on reference number
	 */
	public Reservation getReservationByReference(String referenceNum) {
		ArrayList<Reservation> reservationList = DataHandler.getAllData(new Reservation());
		for (Reservation i : reservationList)
			if (i.isValid() && i.getUUID().equalsIgnoreCase(referenceNum))
				return i;
		return null;
	}

	/**
	 * IO method for getReservationByReference
	 * 
	 * @return the Reservation object retrieved based on name and contact
	 */
	public Reservation doReservationByNameAndContact() {
		Reservation reservation = null;
		String name = "";
		name = IOHelper.getString(true, "Please insert name linked to reservation: ",
				"Please ensure you are typing in a string value");
		String contact = "";
		contact = IOHelper.getString(true, "Please insert contact number linked to reservation: ",
				"Please ensure you are typing in a string value");
		reservation = getReservationByNameAndContact(name, contact);
		if (reservation == null)
			System.out.println("Unable to find a reservation by " + name + " with contact number " + contact);
		else {
			System.out.println(reservation.getReservationInfo());
		}
		return reservation;
	}
	
	/**
	 * Get reservation by customer name and contact number
	 * 
	 * @param name The name of the customer
	 * @param contact The contact number of the customer
	 * @return the Reservation object based retrieved based on reference number
	 */
	public Reservation getReservationByNameAndContact(String name, String contact) {
		ArrayList<Reservation> reservationList = DataHandler.getAllData(new Reservation());
		for (Reservation i : reservationList)
			if (i.isValid() && i.getName().equalsIgnoreCase(name) && i.getContactNumber().equalsIgnoreCase(contact))
				return i;
		return null;
	}

	/**
	 * IO method for getReservationByTableNumber 
	 * 
	 * @return the Reservation object based retrieved based on table number, returns null if no reservation
	 */
	public Reservation doReservationByTableNumber() {
		Reservation reservation = null;
		int tableNum = -1;
		while (tableNum == -1) {
			System.out.print("Please enter table number: ");
			tableNum = IOHelper.getInt();
		}

		reservation = getReservationByTableNumber(tableNum);
		if (reservation == null)
			System.out.println("Table number #" + String.valueOf(tableNum) + " is not reserved.");
		else {
			System.out.println(reservation.getReservationInfo());
		}
		return reservation;
	}
	
	/**
	 * Get reservation by table number
	 * 
	 * @param tableNumber The table number to check for reservation
	 * @return the Reservation object based retrieved based on table number
	 */
	public Reservation getReservationByTableNumber(int tableNumber) {
		ArrayList<Reservation> reservationList = DataHandler.getAllData(new Reservation());
		for (Reservation i : reservationList)
			if (i.isValid() && i.getAssignedTable().getTableNumber() == tableNumber)
				return i;
		return null;
	}

	/**
	 * IO print to list all valid and active reservations
	 */
	public void doListAllReservations() {
		ArrayList<Reservation> reservationList = DataHandler.getAllData(new Reservation());
		ArrayList<Reservation> validList = new ArrayList<Reservation>();

		for (Reservation i : reservationList) {
			i.checkInvalidate();
			if (i.isValid()) {
				validList.add(i);
				System.out.println(i.toString());
			}
		}
		System.out.println(
				"There are a total of " + String.valueOf(validList.size()) + " valid & active reservation(s).\n");
	}
}
