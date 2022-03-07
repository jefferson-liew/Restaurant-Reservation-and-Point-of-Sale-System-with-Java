package oodp.data;

import oodp.data.base.BaseData;

/**
 * Person
 */
public class Person extends BaseData {
	String name;
	String gender;
	/**
	 * Gets the name of this Person
	 * @return this Person's name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Sets the name of this Person
	 * @param name the name of this Person
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Gets the gender of this person
	 * @return this Person's gender
	 */
	public String getGender() {
		return gender;
	}
	/**
	 * Set this Person's gender
	 * @param gender the gender of this Person
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
}
