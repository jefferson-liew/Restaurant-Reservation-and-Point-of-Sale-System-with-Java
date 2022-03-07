package oodp.data;

import oodp.data.base.BaseData;
/**
 * Table
 */
public class Table extends BaseData {
	int tableNumber;
	int capacity;
	int capacityMin = 2;
	int capacityMax = 10;
	boolean isReserved = false;

	/**
	 * Empty constructor for DataHandler to access class name
	 */
	public Table() {
		super();
	}

	/**
	 * Constructor for table
	 * 
	 * @param tableNumber        Table number
	 * @param capacity           Table capacity
	 */
	public Table(int tableNumber, int capacity) {
		super();
		this.tableNumber = tableNumber;
		this.capacity = capacity;
	}

	/**
	 * Constructor for table. This constructor is not currently used. 
	 * It may be used in future updates
	 * 
	 * @param tableNumber        Table number
	 * @param capacity           Table capacity
	 * @param capacityMin        Table minimum capacity if a range is needed
	 * @param capacityMax        Table maximum capacity if a range is needed
	 */
	public Table(int tableNumber, int capacity, int capacityMin, int capacityMax) {
		super();
		this.tableNumber = tableNumber;
		this.capacity = capacity;
		this.capacityMin = capacityMin;
		this.capacityMax = capacityMax;
	}
	
	/**
	 * Get table number
	 * @return The table number
	 */
	public int getTableNumber() {
		return tableNumber;
	}
	
	/**
	 * Get capacity
	 * @return The table's capacity
	 */
	public int getCapacity() {
		return capacity;
	}
	
	/**
	 * Sets the table reserved value to true
	 */
	public void reserve() {
		isReserved = true;
	}

	/**
	 * Sets the table reserved value to false
	 */
	public void unreserve() {
		isReserved = false;
	}
	
	/**
	 * Sets the table reserved value to false
	 * @return A boolean value to indicate whether the table is reserved a not
	 */
	public boolean isReserved() {
		return this.isReserved;
	}
	
	/**
	 * Gets the table information in a simple formatted string
	 * @return Returns a simple string containing the table's information
	 */
	public String getTableInfo() {
		return "Table Number #" + String.valueOf(tableNumber) + " with seating capacity of " + String.valueOf(capacity);
	}

}
