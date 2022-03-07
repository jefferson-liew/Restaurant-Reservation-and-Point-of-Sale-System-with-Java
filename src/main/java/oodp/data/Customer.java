package oodp.data;
/**
 * Customer
 */
public class Customer extends Person {
	boolean isMember;

	/**
	 * Empty constructor for DataHandler to access class name
	 */
	public Customer() {
		super();
	}

	/**
	 * Overloaded constructor for Customer
	 * @param isMember The membership status of this Customer
	 */
	public Customer(boolean isMember) {
		super();
		this.isMember = isMember;
	}

	/**
	 * Constructor for Customer 
	 * @param name The name of this Customer
	 * @param gender The gender of this Customer
	 * @param isMember The membership status of this Customer
	 */
	public Customer(String name, String gender, boolean isMember) {
		super();
		this.name = name;
		this.gender = gender;
		this.isMember = isMember;
	}
	/**
	 * Gets the membership status of this customer
	 * @return This Customer's membership status
	 */

	public boolean isMember() {
		return isMember;
	}

	/**
	 * Sets the membership status of this Customer
	 * @param isMember This Customer's new membership status
	 */
	public void setMember(boolean isMember) {
		this.isMember = isMember;
	}
}
