package oodp.modules;

import java.lang.ModuleLayer.Controller;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Date;


import javax.xml.crypto.Data;

import org.apache.logging.log4j.core.config.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import oodp.DataHandler;
import oodp.data.Invoice;
import oodp.data.MenuItem;
import oodp.data.OrderedItem;
import oodp.data.PromotionSet;
import oodp.data.Reservation;
import oodp.data.Staff;
import oodp.data.Table;
import oodp.helper.IOHelper;
/**
 * Order module, boundary class that handles all order related functions
 */
public class OrderModule implements IModule {
	private static Logger logger = LoggerFactory.getLogger(OrderModule.class);
	public void process(int choice) {
		int inputVal = -1;
		switch (choice) {
		case 1:
			// Create order
			processCreateOrder();
			break;
		case 2:
			// Update order
			while (inputVal == -1) {
				System.out.println("1) Update order by table number");
				System.out.println("2) Go Back");
				inputVal = IOHelper.getInt(1, 2, true, 3);
				if (inputVal == 1) updateOrder();
			}
			break;
		case 3:
			// Delete order
			while (inputVal == -1) {
				System.out.println("1) Delete order by table number");
				System.out.println("2) Go Back");
				inputVal = IOHelper.getInt(1, 2, true, 3);
				if (inputVal == 1) deleteOrder();
			}
			break;
		case 4:
			// Print order invoice
			while (inputVal == -1) {
				System.out.println("1) Print order invoice by table number");
				System.out.println("2) Go Back");
				inputVal = IOHelper.getInt(1, 2, true, 3);
				if (inputVal == 1) printOrderInvoice();
			}
			break;
		}
	}
	/**
	 * calls function to print order invoice
	 */
	void printOrderInvoice() {
		int inputVal = -1;
		int tableNumber;

		tableNumber = checkTable();
		viewOrderInvoice(tableNumber);

		while (inputVal == -1) {
			System.out.println("1) Archive Invoice?");
			System.out.println("2) Go Back");
			inputVal = IOHelper.getInt(1, 2, true);
			if (inputVal == 1) archiveOrderInvoice(tableNumber);
		}
	}
	
	/**
	 * creates an invoice of ordereditems by table number and deletes all orders from the table number
	 * prints out the price to charge the customer
	 * @param tableNumber identifier of the order
	 */
	void archiveOrderInvoice(int tableNumber) {
		// addOrderInvoices into Invoices.java to store the invoices
		String staffID;
		ArrayList <OrderedItem> orderedList;
		float totalPrice=0;

		orderedList = getOrderedList(tableNumber);
		staffID = getStaffID(tableNumber);

		for (OrderedItem items : orderedList) {
			totalPrice += items.getPrice();
		}

		Invoice newinvoice = new Invoice(staffID, tableNumber, orderedList, totalPrice, hasMembership(tableNumber));
		DataHandler.saveData(newinvoice);
		if (newinvoice.isMember()) System.out.println("Please charge the customer: $" + newinvoice.getDiscountedPrice());
		else System.out.println("Please charge the customer: $" + newinvoice.getPrice());
		deleteAll(tableNumber);
	}

	/**
	 * Prompts user to input table number and shows the order invoice of inputted table before calling deletePrompt()
	 */
	void deleteOrder() {
		int tableNumber;
		tableNumber = checkTable();
		viewOrderInvoice(tableNumber);
		deletePrompt(tableNumber);
	}

	/**
	 * displays 2 choices to the user, delete AlaCarte Item or delete Set Item
	 * @param tableNumber identifier of the order
	 */
	void deletePrompt(int tableNumber) {
		int inputVal = -1;

		while (inputVal == -1) {
			System.out.println("1) Delete AlaCarte Menu Item");
			System.out.println("2) Delete Set, PromotionalSet Item");
			inputVal = IOHelper.getInt(1, 2);
		}
		deleteItem(tableNumber, inputVal);
	}

	/**
	 * Deletes item indicated by the input prompt
	 * @param tableNumber identifier of the order
	 * @param inputVal IOHelper variable
	 */
	void deleteItem(int tableNumber, int inputVal) {
		switch (inputVal) {
			case 1:
				String itemName = IOHelper.getString(true, "Please enter name for AlaCarte item: ", "Please enter a valid name. ");
				// System.out.println("Please enter price for AlaCarte item");
				// float itemPrice = IOHelper.getFloat();

				ArrayList<OrderedItem> orderList = DataHandler.getAllData(new OrderedItem());
				for (OrderedItem i : orderList) {
					if (i.getTable() == tableNumber && i.checkSet() == false && i.getItem().equals(itemName)) {
						DataHandler.removeData(i);
						System.out.println("AlaCarte item removed from order");
						break;
					}
				}

				break;
			case 2:
				String setName = IOHelper.getString(true, "Please enter name for set item: ", "Please enter a valid name. ");
				// System.out.println("Please enter price for set item");
				// float setPrice = IOHelper.getFloat();
				
				ArrayList<OrderedItem> orderSetList = DataHandler.getAllData(new OrderedItem());
				for (OrderedItem i : orderSetList) {
					if (i.getTable() == tableNumber && i.checkSet() == true && i.getItem().equals(setName)) {
						DataHandler.removeData(i);
						System.out.println("Set item removed from order");
						break;
					}
				}

				break;
		}
	}

	/**
	 * Prompts user to input table number and shows the order invoice of inputted table before getting 
	 * the user to confirm that the he would like to proceed with update.
	 */
	void updateOrder() {
		int tableNumber;
		String staffID = null;
		int inputVal = -1;

		tableNumber = checkTable();
		viewOrderInvoice(tableNumber);

		ArrayList<OrderedItem> orderList = DataHandler.getAllData(new OrderedItem());
        for (OrderedItem i : orderList) {
            if (i.getTable() == tableNumber) {
                staffID = i.getStaff();
                break;
            }
        }

		if (staffID != null) {
			while (inputVal == -1) {
				System.out.println("1) Add order items to tableNumber: " + tableNumber + " served by " + staffID);
				System.out.println("2) Exit");
				inputVal = IOHelper.getInt(1, 2, true, 3);
				if (inputVal == 1) {
					itemPrompt(staffID, tableNumber);
					inputVal = -1;
				}
			}
		}
		else {
			System.out.println("Order has not been initialised. Starting 'Create Order'");
			processCreateOrder();
		}
	}
	/**
	 * Prompts user to input staffID followed by tableNumber
	 * Gives user the option to add order items
	 */
	void processCreateOrder() {
		int tableNumber;
		String staffID;
		int inputVal = -1;

		staffID = checkStaff();
		tableNumber = checkTable();

		while (inputVal == -1) {
			System.out.println("1) Add order items to tableNumber: " + tableNumber + " served by " + staffID);
			System.out.println("2) Exit");
			inputVal = IOHelper.getInt(1, 2, true, 3);
			if (inputVal == 1) {
				itemPrompt(staffID, tableNumber);
				inputVal = -1;
			}
		}
	}

	/**
	 * Gives user the option to choose between adding AlaCarte item and PromotionalSet item
	 * @param staffID
	 * @param tableNumber
	 */
	void itemPrompt(String staffID, int tableNumber){
		int inputVal = -1;

		while (inputVal == -1) {
			System.out.println("1) Add AlaCarte Menu Item");
			System.out.println("2) Add Set, PromotionalSet Item");
			inputVal = IOHelper.getInt(1, 2);
		}
		addItem(staffID, tableNumber, inputVal);
	}

	/**
	 * Adds item indicated by the input prompt
	 * @param staffID staff identifier
	 * @param tableNumber order identifier
	 * @param inputVal used for IOHelper
	 */
	void addItem(String staffID, int tableNumber, int inputVal) {
		switch (inputVal) {
			case 1:
				String itemName = IOHelper.getString(true, "Please enter name for AlaCarte item: ", "Please enter a valid name. ");
				System.out.println(itemName);
				// System.out.println("Please enter price for AlaCarte item");
				// float itemPrice = IOHelper.getFloat();
				ArrayList<MenuItem> menuList = DataHandler.getAllData(new MenuItem());
				for (MenuItem i : menuList) {
					if (i.getName().equals(itemName)) {
						OrderedItem orderedItem	= new OrderedItem(staffID, tableNumber, i.getName(), i.getPrice(), false);
						DataHandler.saveData(orderedItem);
						System.out.println("AlaCarte item added to the order!");
						break;
					}
				}
				break;
			case 2:
				String setName = IOHelper.getString(true, "Please enter name for set item: ", "Please enter a valid name. ");
				// System.out.println("Please enter price for set item");
				// float setPrice = IOHelper.getFloat();
				ArrayList<PromotionSet> promoList = DataHandler.getAllData(new PromotionSet());
				for (PromotionSet i : promoList) {
					if (i.getName().equals(setName)) {
						OrderedItem orderedItem	= new OrderedItem(staffID, tableNumber, i.getName(), i.getPrice(), true);
						DataHandler.saveData(orderedItem);
						System.out.println("Set item added to the order!");
						break;
					}
				}
				break;
		}
	}

	/**
	 * Prompts user to enter table number
	 * @return integer tableNumber
	 */
	int checkTable() {
		int tableNumber;
		while(true) {
			// System.out.println("To exit, enter -1");
			System.out.print("Please enter table number: ");
			tableNumber = IOHelper.sc.nextInt();
			if (tableOrderValid(tableNumber)) return tableNumber;

			// else if (tableNumber == -1) return -1;

			else System.out.println("Table number not valid");
		}
	}
	/**
	 * Prompts user to enter staffID
	 * @return String integer
	 */
	String checkStaff() {
		String staffID;
		Staff staff;
		while(true) {
			staffID = IOHelper.getString(true, "Please enter staff id: ", "Please enter a valid staff id. ");

			staff = staffIDValid(staffID);
			if (staff != null)
			{
				System.out.println("Staff found: ");
				System.out.println(staff.getStaffId()+", "+staff.getStaffName()+", "+staff.getJobTitle());
				System.out.println();
				return staff.getStaffId();
			}
			else System.out.println("Staff ID not found.");
		}
	}

	/**
	 * checks if table has been marked as reserved
	 * @param tableNumber identifier of order
	 * @return true when table is marked as reserved
	 */
	boolean tableOrderValid(int tableNumber) {
		ArrayList<Table> tableList = DataHandler.getAllData(new Table());
		for (Table i : tableList) {
			if (i.getTableNumber() == tableNumber) {
				if (i.isReserved())
					return true;
				else {
					System.out.println("Table #" + String.valueOf(tableNumber) + " has not been marked as occupied. Please create reservation!");
					return false;
				}
			}
		}
		System.out.println("Table #" + String.valueOf(tableNumber) + " is not found.");
		return false;
	}

	/**
	 * checks if staffID is valid even with incomplete staffID
	 * @param staffID identifier of staff
	 * @return full staffID
	 */
	Staff staffIDValid(String staffID) {
		ArrayList<Staff> staffList = DataHandler.getAllData(new Staff());
		for (Staff i : staffList) {
			if(i.getStaffId().matches("(.*)"+staffID)) {
				return i;
			}
		}
		return null;
	}

	/**
	 * prints out order invoice and grouping items with same name together
	 * @param tableNumber identifier of order
	 */
	public void viewOrderInvoice(int tableNumber) {
        float invoicePrice = 0;
        
        ArrayList<OrderedItem> orderList = DataHandler.getAllData(new OrderedItem());
        System.out.println("Order List for table number: " + tableNumber);
		System.out.println("(Set) Item Name \t Quantity \t Price");

		ArrayList<OrderedItem> invoiceDupItems = new ArrayList<OrderedItem>();
		for (OrderedItem i : orderList) {
            if (i.getTable() == tableNumber) {
				invoicePrice += i.getPrice();
				invoiceDupItems.add(i);
            }
        }

		ArrayList <String> nameOnly = new ArrayList<String>();
		for (OrderedItem names : invoiceDupItems) {
			nameOnly.add(names.getItem());
		}

		// Set<OrderedItem> distinctItems = new HashSet<>(invoiceDupItems);
		ArrayList <OrderedItem> distinctItems = new ArrayList<OrderedItem>();
		ArrayList <String> distinctName = new ArrayList<String>();
		for (OrderedItem items : invoiceDupItems) {
			if(!distinctName.contains(items.getItem())) {
				distinctName.add(items.getItem());
				distinctItems.add(items);
			} 
		}

		for (OrderedItem j : distinctItems) {
			if (j.checkSet()) {
				System.out.println("Set: " + j.getItem() + "\t " + Collections.frequency(nameOnly, j.getItem()) + "\t $" + (j.getPrice()*Collections.frequency(nameOnly, j.getItem())));
			}
			else {
				System.out.println(j.getItem() + "\t " + Collections.frequency(nameOnly, j.getItem()) + "\t $" + (j.getPrice()*Collections.frequency(nameOnly, j.getItem())) );
			}
		}

        System.out.println("Total: $" + invoicePrice);
        // System.out.println("Tax 7%: $" + invoicePrice*0.07);
        // System.out.println("Service Fee 10%: $" + invoicePrice*0.10);
		
        // float totalPayable = (invoicePrice + invoicePrice*0.07f + invoicePrice*0.10f);
        // System.out.println("Total Payable: $" + totalPayable);
    }

	/**
	 * checks if the customer has a membership
	 * @param tableNumber identifier of order
	 * @return true when customer has a membership
	 */
	boolean hasMembership(int tableNumber) {
		String membershipId = IOHelper.getString(true, "Please enter membership ID (leave blank if not applicable): ", "Please enter a valid string");
		if (membershipId.trim().equals("")) return false;
		else return true;
	}

	/**
	 * gets the ArrayList<OrderedItem> with tableNumber
	 * @param tableNumber identifier of order
	 * @return arraylist of ordereditems 
	 */
	ArrayList <OrderedItem> getOrderedList(int tableNumber) {
		ArrayList<OrderedItem> orderList = DataHandler.getAllData(new OrderedItem());
		ArrayList<OrderedItem> invoiceDupItems = new ArrayList<OrderedItem>();
		for (OrderedItem i : orderList) {
            if (i.getTable() == tableNumber) {
				invoiceDupItems.add(i);
            }
        }
		return orderList;	
	}

	/**
	 * gets the staff ID
	 * @param tableNumber identifier of order
	 * @return full StaffID
	 */
	String getStaffID(int tableNumber) {
		ArrayList<OrderedItem> orderList = DataHandler.getAllData(new OrderedItem());
		for (OrderedItem i : orderList) {
            if (i.getTable() == tableNumber) {
				return i.getStaff();
            }
        }
		return null;
	}

	/**
	 * removes all orderitem with passed in tableNumber. function is called after archiving to invoice is performed
	 * @param tableNumber identifier of order
	 */
	void deleteAll(int tableNumber) {
		ArrayList<OrderedItem> orderSetList = DataHandler.getAllData(new OrderedItem());
		for (OrderedItem i : orderSetList) {
			if (i.getTable() == tableNumber) {
				DataHandler.removeData(i);
			}
		}
		System.out.println("All ordered items by table number: " + tableNumber + " has been removed.");
	}
}