package oodp.modules;

import java.util.ArrayList;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import oodp.AppConstants;
import oodp.DataHandler;
import oodp.data.Reservation;
import oodp.data.Table;
import oodp.helper.IOHelper;
import oodp.modules.controllers.ReservationController;

/**
 * Reservation module, boundary class that handles all reservation related functions
 */
public class ReservationModule implements IModule {
	private static Logger logger = LoggerFactory.getLogger(ReservationModule.class);
	private ReservationController controller = new ReservationController();
	
	public void process(int choice) {
		int inputVal = -1;
		switch (choice) {
		case 1:
			// Check reservation
			while (inputVal == -1) {
				System.out.println("================ Check reservation ================");
				System.out.println("1) By reference number");
				System.out.println("2) By name and contact number");
				System.out.println("3) By table number");
				System.out.println("4) List all reservations");
				System.out.println("5) Go back");
				System.out.print("Please enter a number from 1 to 4: ");
				inputVal = IOHelper.getInt(1, 5, true, 4);
				controller.checkReservationInvalidate();
				processCheckReservation(inputVal);
			}
			inputVal = -1;
			break;
		case 2:
			// Check Table Availability
			while (inputVal == -1) {
				System.out.println("================ Check table availability ================");
				System.out.println("1) By table number");
				System.out.println("2) By pax");
				System.out.println("3) Go back");
				System.out.print("Please enter a number from 1 to 3: ");
				inputVal = IOHelper.getInt(1, 3, true, 4);
				controller.checkReservationInvalidate();
				processCheckTableAvailability(inputVal);
			}
			inputVal = -1;
			break;
		case 3:
			// Create reservation
			controller.checkReservationInvalidate();
			processCreateReservation();
			break;
		/*
		 * case 4: // Update reservation while (inputVal == -1) {
		 * System.out.println("================ Update a reservation ================");
		 * System.out.println("1) By reference number");
		 * System.out.println("2) By name and contact number");
		 * System.out.println("3) Go back");
		 * System.out.print("Please enter a number from 1 to 3: "); inputVal =
		 * IOHelper.getInt(1, 3, true, 4); checkReservationInvalidate();
		 * processUpdateReservation(inputVal); } inputVal = -1; break;
		 */
		case 4:
			// Remove reservation
			while (inputVal == -1) {
				System.out.println("================ Delete a reservation ================");
				System.out.println("1) By reference number");
				System.out.println("2) By name and contact number");
				System.out.println("3) Go back");
				System.out.print("Please enter a number from 1 to 3: ");
				inputVal = IOHelper.getInt(1, 3, true, 4);
				controller.checkReservationInvalidate();
				processDeleteReservation(inputVal);
			}
			inputVal = -1;
			break;
		}
	}

	void processCheckReservation(int choice) {
		if (choice == -1)
			return;
		switch (choice) {
		case 1:
			// Check reservation by reservation reference number
			controller.doGetReservationByReference();
			break;
		case 2:
			// Check reservation by name & contact
			controller.doReservationByNameAndContact();
			break;

		case 3:
			// Check reservation by table number
			controller.doReservationByTableNumber();
			break;

		case 4:
			// List all reservations
			controller.doListAllReservations();
			break;
		}
	}

	void processCheckTableAvailability(int choice) {
		if (choice == -1)
			return;

		switch (choice) {
		case 1:
			int tableNum = -1;
			while (tableNum == -1) {
				System.out.print("Please enter table number: ");
				tableNum = IOHelper.getInt();
				try {
					if (controller.isTableReserved(tableNum)) {
						System.out.println("Table #" + String.valueOf(tableNum) + " is reserved.");
						System.out.println(controller.getReservationByTableNumber(tableNum).getReservationInfo());
					} else {
						System.out.println("Table #" + String.valueOf(tableNum) + " is available for reservation");
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			break;
		case 2:
			int pax = -1;
			while (pax == -1) {
				System.out.print("Please enter number of pax: ");
				pax = IOHelper.getInt();
				ArrayList<Table> validTables = controller.getTableAvailabilityByPax(pax);
				System.out.println("There are " + String.valueOf(validTables.size()) + " suitable tables");
				for (Table i : validTables) {
					System.out.println(i.getTableInfo());
				}
			}

			break;

		}
	}

	void processCreateReservation() {
		String name = IOHelper.getString(1, 50, true, "Please enter customer name: ", "Please enter a valid string");
		String contactNumber = IOHelper.getString(1, 50, true, "Please enter customer contact: ",
				"Please enter a valid string");
		String membershipId = IOHelper.getString(true, "Please enter membership ID (leave blank if not applicable): ",
						"Please enter a valid string");
		int pax = -1;
		while (pax == -1) {
			System.out.print("Please insert pax count: ");
			pax = IOHelper.getInt(1, Integer.MAX_VALUE);
		}
		Date dateTime = IOHelper.getDateTimeAfter(new Date(), AppConstants.FORMAT_DATETIME_INPUT_NO_SECONDS, true,
				"Please enter date and time (" + AppConstants.FORMAT_DATETIME_INPUT_NO_SECONDS + ")[24 hour clock] : ",
				"There was an error parsing your input, please ensure you follow the provided format");
		Reservation reservation = new Reservation(dateTime, pax, name, contactNumber, membershipId);
		boolean hasTable = controller.assignReservationTable(reservation);
		if (hasTable) {
			boolean success = DataHandler.saveData(reservation);
			if (success) {
				System.out.println("Reservation was made successfully!");
				System.out.println(reservation.getReservationInfo());
			}
		} else {
			System.out
					.println("Reservation was unsuccessful as there are no available tables with matching criterias\n");
		}
	}

	/*
	 * void processUpdateReservation(int choice) { if (choice == -1) return;
	 * Reservation reservation = null; switch (choice) { case 1: // Update
	 * reservation by reservation reference number reservation =
	 * doGetReservationByReference(); break; case 2: // Update reservation by name &
	 * contact reservation = doReservationByNameAndContact(); break; }
	 * 
	 * if (reservation != null) { reservation.getReservationInfo(); int inputVal =
	 * -1; while (inputVal == -1) { System.out.println(
	 * "================ Updating reservation " + reservation.getUUID() +
	 * " ================"); System.out.println("1) Customer Name");
	 * System.out.println("2) Customer Contact Number");
	 * System.out.println("3) Pax Count");
	 * System.out.println("4) Reservation Date & Time");
	 * System.out.println("5) Stop updating");
	 * System.out.print("Please enter a number from 1 to 5: "); inputVal =
	 * IOHelper.getInt(1, 6, false, 0);
	 * 
	 * switch (inputVal) { case 1: String customerName = IOHelper.getString(0, 50,
	 * true, "Please insert new value of Customer Name: ",
	 * "Please insert a valid string"); if (!customerName.trim().equals(""))
	 * reservation.setName(customerName); break; case 2: String contactNumber =
	 * IOHelper.getString(0, 50, true,
	 * "Please insert new value of Customer Contact Number: ",
	 * "Please insert a valid string"); if (!contactNumber.trim().equals(""))
	 * reservation.setContactNumber(contactNumber); break; case 3: int pax =
	 * IOHelper.getInt(1, Integer.MAX_VALUE)
	 * 
	 * break; case 4: break; case 5: break; } if (inputVal != 5) inputVal = -1; else
	 * { String input = IOHelper.getString(1, 2, true,
	 * "Woud you like to save your changes? (Y/N): ",
	 * "Please enter a valid string"); if (input.toLowerCase().equals(("y"))) {
	 * DataHandler.saveData(reservation); } } } checkReservationInvalidate();
	 * 
	 * } else System.out.
	 * println("Unable to find a valid reservation with matching criterias"); }
	 */

	void processDeleteReservation(int choice) {
		if (choice == -1)
			return;
		Reservation reservation = null;
		switch (choice) {
		case 1:
			// Update reservation by reservation reference number
			reservation = controller.doGetReservationByReference();
			break;
		case 2:
			// Update reservation by name & contact
			reservation = controller.doReservationByNameAndContact();
			break;
		}
		if (reservation != null) {
			reservation.getReservationInfo();
			String input = IOHelper.getString(1, 2, true, "Are you sure you want to delete this reservation? (Y/N): ",
					"Please enter a valid string");
			if (input.toLowerCase().equals(("y"))) {
				reservation.invalidate();
				boolean success = DataHandler.saveData(reservation);
				if (success)
					System.out.println("The reservation has been successfully deleted!");
			}
		} else
			System.out.println("Unable to find a valid reservation with matching criterias");
	}
}
