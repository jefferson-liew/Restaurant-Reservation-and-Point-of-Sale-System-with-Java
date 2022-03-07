package oodp.data;

import oodp.data.base.BaseData;
/** *
 * Represents a menu item
 */
public class MenuItem extends BaseData {
	/** *
	 * The name of this MenuItem
	 */
	String name;
	/** *
	 * The description of this MenuItem
	 */
	String description;
	/** *
	 * The price of this MenuItem
	 */
	float price;
	/** *
	 * The type of this MenuItem
	 */
	String type;

	/**
	 * Empty constructor for DataHandler to access class name
	 */
	public MenuItem() {
		super();
	}

	/** *
	 * Creates a new MenuItem with the given params
	 * @param name The name of this MenuItem
	 * @param description The description of this MenuItem
	 * @param price The price of this MenuItem
	 * @param type The type of this MenuItem
	 */
	public MenuItem(String name, String description, float price, String type) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
		this.type = type;
	}
	/** *
	 * Changes the type of this MenuItem
	 * @param type This MenuItem's new type.
	 */
	public void setType(String type) {
		this.type = type;
	}
	/** *
	 * Changes the name of this MenuItem
	 * @param name This MenuItem's new name.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/** *
	 * Changes the description of this MenuItem
	 * @param description This MenuItem's new description.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/** *
	 * Changes the price of this MenuItem
	 * @param price This MenuItem's new price. 
	 * Should be a float.
	 */
	public void setPrice(float price) {
		this.price = price;
	}

	/** *
	 * Gets the type of this MenuItem
	 * @return This MenuItem's type
	 */
	public String getType() {
		return this.type;
	}
	/** *
	 * Gets the name of this MenuItem
	 * @return This MenuItem's name
	 */
	public String getName() {
		return this.name;
	}
	/** *
	 * Gets the description of this MenuItem
	 * @return This MenuItem's description
	 */
	public String getDescription() {
		return this.description;
	}
	/** *
	 * Gets the price of this MenuItem
	 * @return This MenuItem's price
	 */
	public float getPrice() {
		return this.price;
	}
	/**
	 * Prints of the params of this MenuItem
	 */
	public void printItem() {
		System.out.println("Name: " + this.name);
		System.out.println("Description: " + this.description);
		System.out.println("Price: $" + this.price);
		System.out.println("Type: " + this.type);
		System.out.println();
	}
}
