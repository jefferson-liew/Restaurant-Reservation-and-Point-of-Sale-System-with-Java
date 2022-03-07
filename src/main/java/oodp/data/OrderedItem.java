package oodp.data;

import oodp.data.base.BaseData;
/**
 * OrderedItem
 */
public class OrderedItem extends BaseData{
    String staffID;
    int tableNumber;
    String itemName;
    float itemPrice;
    boolean isSet;
    
/**
 * Constructor for OrderedItem
 * @param staffID The staffID of this OrderedItem
 * @param tableNumber The table number of this OrderedItem
 * @param itemName The name of the OrderedItem
 * @param itemPrice The price of the OrderedItem
 * @param isSet If this OrderedItem is part of a set
 */
    public OrderedItem(String staffID, int tableNumber, String itemName, float itemPrice, boolean isSet) {
        super();
        this.staffID = staffID;
        this.tableNumber = tableNumber;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.isSet = isSet;
    }

    /**
	 * Empty constructor for DataHandler to access class name
	 */
    public OrderedItem() {
        super();
    }
    /**
     * Gets the table number of this OrderedItem
     * @return this OrderItem's table number
     */

    public int getTable() {
        return this.tableNumber;
    }
    /**
     * Gets the price of this OrderedItem
     * @return this OrderedItem's price
     */
    public float getPrice(){
        return this.itemPrice;
    }

    /**
     * Gets the name of the OrderedItem
     * @return this OrderedItem's name
     */
    public String getItem() {
        return this.itemName;
    }
    /**
     * Checks if this OrderedItem is part of a set
     * @return this OrderItem's set status
     */
    public boolean checkSet() {
        return this.isSet;
    }
    /**
     * Gets the staffID of this OrderedItem
     * @return this OrderedItem's staffID
     */

    public String getStaff() {
        return this.staffID;
    }
}
