/**
 * Promotional Sets made of items from the menu
 * @author Evan Sean Sainani
 * 
 */

package oodp.data;

import java.util.Date;
import java.util.ArrayList;

import oodp.data.base.BaseData;
/**
 * PromotionSet
 */
public class PromotionSet extends BaseData {
	
	String promoName;
	String promoDescription;
	float promoPrice;
	ArrayList<MenuItem> promoItems = new ArrayList<MenuItem>();
	Date startDate;
	Date endDate;
	/**
	 * Constructor for the promotion set, creates a promotion set with the below parameters
	 * @param setName This will be the name of the promotion set to be created
	 * @param setPrice This will be the price of the promotion set to be created
	 * @param setItems This will be the menu items contained in the promotion set to be created
	 * @param startDate This is the start date for the availability of the promotion set to be created
	 * @param endDate This is the end date for the availability of the promotion set to be created
	 */
	public PromotionSet(String setName,float setPrice,ArrayList<MenuItem> setItems,java.util.Date startDate,java.util.Date endDate) {
		super();
		this.promoName = setName;
		this.promoDescription = setName + "set includes:\n";
		for(MenuItem item : setItems){
			this.promoDescription = this.promoDescription + item.description + "\n";
		}
		this.promoPrice = setPrice;
		this.promoItems = setItems;
		this.startDate = startDate;
		this.endDate = endDate;
		this.promoDescription = this.promoDescription + "Promotion starts on " + startDate.toString() + " and ends on " + endDate.toString();
	}
	/**
	 * Empty Constructor for DataHandler to access class name
	 */
	public PromotionSet() {
		super();
	}

	//Mutator Methods
	/**
	 * Sets name of the promotion set
	 * @param name This will be set as the name of the promotion set 
	 */
	public void setName(String name) {
		this.promoName = name;
	}
	/**
	 * Sets start date for the availability of the promotion set to be created
	 * @param startDate This will be set as the start date for the availability of the promotion set to be created
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * Sets end date for the availability of the promotion set to be created 
	 * @param endDate This will be set as the end date for the availability of the promotion set to be created
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * This sets the description of the promotional set, which contains details of the items in the promotional set, and the promotional period
	 * @param setName This is the name of the promotional set, it is used in the description
	 * @param setItems These are the items included in the promotion set, their description will be included in the description of the promotion set
	 */
	public void setDescription(String setName, ArrayList<MenuItem> setItems) {
		this.promoDescription = setName + " set includes:\n";
		for(MenuItem item : setItems){
			this.promoDescription = this.promoDescription + item.description + "\n";
		}
		this.promoDescription = this.promoDescription + "Promotion starts on " + this.getStartDate().toString() + " and ends on " + this.getEndDate().toString();
	}
	/**
	 * This sets the price of the promotion set
	 * @param price This is the desired price to be set for the promotion set
	 */
	public void setPrice(float price) {
		this.promoPrice = price;
	}
	/**
 	 * This sets the menu items as part of the promotion set
	 * @param promoItems This is a list of items selected from the menu to be part of the promotion set
 	*/
	public void setPromoItems(ArrayList<MenuItem> promoItems) {
		this.promoItems = promoItems;
	}

	//Accessor Methods
	/**
	 * This gets the name of the promotion set
	 * @return the name of the promotion set
	 */
	public String getName() {
		return this.promoName;
	}
	/**
	 * This gets the description of the promotion set
	 * @return the description of the promotion set
	 */
	public String getDescription() {
		this.promoDescription = this.getName() + "set includes:\n";
		for(MenuItem item : this.getPromoItems()){
			this.promoDescription = this.promoDescription + item.description + "\n";
		}
		this.promoDescription = this.promoDescription + "Promotion starts on " + this.getStartDate().toString() + " and ends on " + this.getEndDate().toString();
		return this.promoDescription;
	}
	/**
	 * This gets the price of the promotion set
	 * @return the price of the promotion set
	 */
	public float getPrice() {
		return this.promoPrice;
	}
	/**
	 * This gets the list of menu items that are part of the promotion set
	 * @return the list of menu items that are included in the promotion set
	 */
	public ArrayList<MenuItem> getPromoItems() {
		return this.promoItems;
	}
	/**
	 * This gets the start date for the availability of the promotion set to be created
	 * @return the start date for the availability of the promotion set to be created
	 */
	public Date getStartDate() {
		return this.startDate;
	}
	/**
	 * This gets the end date for the availability of the promotion set to be created
	 * @return the end date for the availability of the promotion set to be created
	 */
	public Date getEndDate() {
		return this.endDate;
	}
}
