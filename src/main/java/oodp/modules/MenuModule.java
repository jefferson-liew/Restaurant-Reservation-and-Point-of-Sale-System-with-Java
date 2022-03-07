package oodp.modules;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import oodp.DataHandler;
import oodp.data.MenuItem;
import oodp.helper.IOHelper;
/** *
 * Represents the Menu
 * A menu can contain multiple Menu Items
 */
public class MenuModule implements IModule{
	private static Logger logger = LoggerFactory.getLogger(MenuModule.class);
	public void process(int choice) {
		switch (choice) {
		case 1:
			//view menu
			viewMenu();
			break;
		case 2:
			//view menu item
			viewItem();
			break;
			
		case 3:
			// Create menu item
			createItem();
			break;
		case 4:
			// Update menu item
			updateItem();
			break;
		case 5:
			// Delete menu item
			deleteItem();
			break;
			
		}
	}
	/**
	 * Prints out the names of all the items on the menu
	 */
	public void viewMenu() {
		ArrayList<MenuItem> Menu = DataHandler.getAllData(new MenuItem());
		//logger.debug("There are " + Menu.size() + " menu items.");
		System.out.println("There are " + Menu.size() + " menu items.");
		if(Menu.size()<1) {
			return;
		}
		for(MenuItem i : Menu) {
			System.out.println(i.getName());
		}
		System.out.println();
		
	}
	/**
	 * Finds an item on the Menu and prints its details
	 */
	public void viewItem() {
		//boolean found = false;
		ArrayList<MenuItem> Menu = DataHandler.getAllData(new MenuItem());
		//logger.debug("There are " + Menu.size() + " menu items.");
		System.out.println("There are " + Menu.size() + " menu items.");
		if(Menu.size()<1) {
			return;
		}
		String name = IOHelper.getString(true,"Please enter the item name: ", "Please enter a valid string");
		for(MenuItem i : Menu) {
			if(i.getName().equals(name)) {
				//found = true;
				i.printItem();
				return;
			}
		}
		System.out.println("Item not found.");
		System.out.println();
	}
	/**
	 * Creates and adds a new MenuItem to the menu
	 */
	public void createItem() {
		String itemName = IOHelper.getString(true,"Please enter the item name: ", "Please enter a valid string");
		String itemDescription = IOHelper.getString(true,"Please enter the description: ", "Please enter a valid string");
		System.out.print("Please enter the price: ");
		float itemPrice = IOHelper.getFloat();
		String itemType = IOHelper.getString(true,"Please enter the item type: ", "Please enter a valid string");
		MenuItem menuitem = new MenuItem(itemName, itemDescription, itemPrice, itemType);
		DataHandler.saveData(menuitem); 
		System.out.println();
	}
	/**
	 * Finds and updates the details of an existing item
	 */
	public void updateItem() {
		//found = false;
		ArrayList<MenuItem> Menu = DataHandler.getAllData(new MenuItem()); // Get staff list
		//logger.debug("There are " + Menu.size() + " menu items.");
		System.out.println("There are " + Menu.size() + " menu items.");
		if(Menu.size()<1) {
			return;
		}
		String name = IOHelper.getString(true,"Please enter the name of item you want to update: ", "Please enter a valid string");
		for (MenuItem i : Menu) {
			if(i.getName().equals(name)) {
				//found = true;
				System.out.println("Leave blank if there are no changes.\n");
				String input = IOHelper.getString(true,"Please enter new name: ", "Please enter a valid string");
				if(!input.isBlank()) {
					i.setName(input);
				}
				input = IOHelper.getString(true,"Please enter new description: ", "Please enter a valid string");
				if(!input.isBlank()) {
					i.setDescription(input);
				}
				System.out.print("Please enter new price: ");
				float inputf = IOHelper.getFloat();
				//System.out.print(inputf);
				if(inputf != -1) {
					i.setPrice(inputf);
				}
				input = IOHelper.getString(true,"Please enter new type: ", "Please enter a valid string");
				if(!input.isBlank()) {
					i.setType(input);
				}
				System.out.println();
				DataHandler.saveData(i);
				return;
			}
		}
		
		System.out.println("Item not found.");
		System.out.println();
	}
	
	/**
	 * Removes an existing MenuItem from the Menu
	 */
	public void deleteItem() {
		//found = false;
		ArrayList<MenuItem> Menu = DataHandler.getAllData(new MenuItem());
		//logger.debug("There are " + Menu.size() + " menu items.");
		System.out.println("There are " + Menu.size() + " menu items.");
		if(Menu.size()<1) {
			return;
		}
		String name = IOHelper.getString(true,"Please enter the item name: ", "Please enter a valid string");
		for (MenuItem i : Menu) {
			if(i.getName().equals(name)) {
				//found = true;
				DataHandler.removeData(i);
				System.out.println("Item removed.");
				System.out.println();
				return;
			}
		}
		
		System.out.println("Item not found.");
		System.out.println();
		
	}
}
