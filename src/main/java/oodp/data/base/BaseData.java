package oodp.data.base;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import oodp.AppConstants;
/**
 * BaseData class that all POJOs (models) should implement so that the DataHandler will be able to act on it
 */
public class BaseData {
	private static Logger logger = LoggerFactory.getLogger(BaseData.class);
	String uuid = null;
	protected boolean isValidForSaving = false;

	/**
	 * Assigns a UUID to the class
	 */
	private void assignUUID() {
		uuid = UUID.randomUUID().toString();
	}

	/**
	 * Retrieves the UUID tagged to this object
	 * 
	 * @return UUID of the object
	 */
	public String getUUID() {
		if (uuid == null)
			assignUUID();
		return uuid;
	}
	
	/**
	 * Retrieves the object in a serialized JSON format with the Gson library
	 * @return JSON serialized formatted string of the object
	 */
	public String getJson() {
		if (uuid == null)
			assignUUID();
		return new Gson().toJson(this);
	}
	
	/**
	 * @return The name of the superclass that inherited BaseData
	 */
	public String getPath() {
		return this.getClass().getSimpleName().toString();
	}
	
	/**
	 * This method will be called by DataHandler before saving to the filesystem
	 * Override this method to have a structured data validation
	 */
	public void validateBeforeSave() {
		if (AppConstants.allowLogging) {
		logger.warn("There is no data validation implemented for this class (" + this.getClass().getName() + ")");
		logger.warn("It is highly recommended for you to implement your own data validation to make sure your data has integrity");
		logger.warn("To implement class data validation, override the 'validateBeforeSave()' method");
		logger.warn("See example override in oodp.data.Reservation class");
		}
		
		isValidForSaving = true;
		

	}


	/**
	 * Checks if the data is valid for saving
	 * @return A boolean flag to note whether the class can be saved by DataHandler
	 */
	public boolean isValidForSaving() {
		return this.isValidForSaving;
	}
}
