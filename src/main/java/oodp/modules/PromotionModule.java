package oodp.modules;

import java.util.ArrayList;
import java.util.Date;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import oodp.AppConstants;
import oodp.DataHandler;
import oodp.data.MenuItem;
import oodp.data.PromotionSet;
import oodp.data.Staff;
import oodp.helper.IOHelper;
/**
 * Promotion module, boundary class that handles all promotion related functions
 */
public class PromotionModule implements IModule {
	private static Logger logger = LoggerFactory.getLogger(PromotionModule.class);
	public void process(int choice) {
		switch (choice) {
		case 1:
			//View Promotion Sets
			viewPromo();
			break;
		case 2:
			// Create promotion
			createPromo();
			break;
		case 3:
			// Update promotion
			updatePromo();
			break;
		case 4:
			// Delete promotion
			deletePromo();
			break;
		}
	}

	void viewPromo(){
			ArrayList<PromotionSet> PromotionSet = DataHandler.getAllData(new PromotionSet()); 
			logger.debug("There are " + PromotionSet.size() + " promotion sets.");
			if(PromotionSet.size()<1) {
				return;
			}
			for(PromotionSet i : PromotionSet) {
				System.out.println(i.getName());
			}
			System.out.println();
	}

	void createPromo(){
			ArrayList<PromotionSet> PromotionSet = DataHandler.getAllData(new PromotionSet()); 
			ArrayList<MenuItem> Menu = DataHandler.getAllData(new MenuItem());
			boolean foundFlag = false;
			boolean dupFlag = false;
			ArrayList<MenuItem> promoItems = new ArrayList<MenuItem>();
			String setName = IOHelper.getString(true,"Please enter the name of the Promotional Set: ", "Please enter a valid string");
			//duplicate name check
			do{
				dupFlag = false;
				for(PromotionSet i : PromotionSet){
					if(i.getName().equals(setName)){
						dupFlag = true;
						System.out.println("This set already exists, please enter another name for the promotional set");
					}
				}
				if(dupFlag){
					setName = IOHelper.getString(true,"Please enter the name of the Promotional Set: ", "Please enter a valid string");
				}
			}while(dupFlag);

			String itemName = IOHelper.getString(true,"Please enter the name of item you wish to add into the set (Case-Sensitive)\nEnter -1 to stop adding items to the set",
			 "Item does not exist, please enter a valid item name");
			while(!itemName.equalsIgnoreCase("-1")){
				foundFlag = false;
				for(MenuItem item : Menu){
					if(item.getName().equals(itemName)){
						foundFlag = true;
						promoItems.add(item);
						System.out.println(item.getName() + " successfully added to " + setName + "!");
					}
				}
				if (foundFlag == false){
					System.out.println("Item not found");
				}
				itemName = IOHelper.getString(true,"Please enter the name of item you wish to add into the set (Case-Sensitive)\nEnter -1 to stop adding items to the set\n",
			 	"Item does not exist, please enter a valid item name");
			}
			System.out.println("Please enter the price of the " + setName + " set: ");
			float setPrice = IOHelper.getFloat();
			Date startDate = IOHelper.getDateTimeAfter(new Date(), AppConstants.FORMAT_DATETIME_INPUT_NO_SECONDS, true,
				"Please enter start date and time (" + AppConstants.FORMAT_DATETIME_INPUT_NO_SECONDS + ")[24 hour clock] : ",
				"There was an error parsing your input, please ensure you follow the provided format");
			Date endDate = IOHelper.getDateTimeAfter(new Date(), AppConstants.FORMAT_DATETIME_INPUT_NO_SECONDS, true,
				"Please enter end date and time (" + AppConstants.FORMAT_DATETIME_INPUT_NO_SECONDS + ")[24 hour clock] : ",
				"There was an error parsing your input, please ensure you follow the provided format");
			PromotionSet promo = new PromotionSet(setName,setPrice, promoItems,startDate,endDate);
			DataHandler.saveData(promo);
			System.out.println(promo.getName() + " set successfully created");
	}

	void updatePromo(){
		int select=0;
		int j = 1;
		int k=0;
		boolean foundFlag = false;
		boolean flag = true;
		boolean foundFlag2 = false;
		boolean dupFlag = false;
		ArrayList<MenuItem> Menu = DataHandler.getAllData(new MenuItem());
		ArrayList<PromotionSet> PromotionSet = DataHandler.getAllData(new PromotionSet()); 
			logger.debug("There are " + PromotionSet.size() + " promotion sets.");
			String name = IOHelper.getString(true,"Please enter the name of promotional set you want to update: ", "Please enter a valid string");
			for (PromotionSet i : PromotionSet) {
				if(i.getName().equals(name)) {
					foundFlag = true;
					flag = true;
					foundFlag2 = false;
					while(flag){
						j=1;
						k=0;
						System.out.println("Select desired change to be made");
						System.out.println("1. Promotional Set Name = " + i.getName());
						System.out.println("2. Promotional Set Price = " + i.getPrice());
						System.out.println("3. Promotional Set Period = " + i.getStartDate().toString() + " - " + i.getEndDate().toString());
						System.out.println("4. Promotional Set Items = " + i.getDescription());
						System.out.println("5. Back");
						select = IOHelper.getInt(1,5,true,2);
						switch(select){
							case 1:
								//Update Promo Name
								String input = IOHelper.getString(true,"Please enter new name: ", "Please enter a valid string");
								do{
									dupFlag = false;
									for(PromotionSet p : PromotionSet){
										if(p.getName().equals(input)){
											dupFlag = true;
											System.out.println("This set already exists, please enter another name for the promotional set");
										}
									}
									if(dupFlag){
										input = IOHelper.getString(true,"Please enter the name of the Promotional Set", "Please enter a valid string");
									}
									
								}while(dupFlag);
								i.setName(input);
								System.out.println("Promotional set successfully renamed to " + i.getName());
								DataHandler.saveData(i);
								break;
							case 2:
								//Update Promo Price
								System.out.println("Please enter new price: ");
								float inputPrice = IOHelper.getFloat();
								i.setPrice(inputPrice);
								System.out.println("Promotional set price successfully updated to " + i.getPrice());
								DataHandler.saveData(i);
								break;
							case 3:
								//Update Promo Period
								System.out.println("1. Edit Start Date");
								System.out.println("2. Edit End Date");
								System.out.println("3. Back");
								k = IOHelper.getInt(1,3,true,2);
								if(k == 1){
									Date editStartDate = IOHelper.getDateTimeAfter(new Date(), AppConstants.FORMAT_DATETIME_INPUT_NO_SECONDS, true,
									"Please enter date and time (" + AppConstants.FORMAT_DATETIME_INPUT_NO_SECONDS + ")[24 hour clock] : ",
									"There was an error parsing your input, please ensure you follow the provided format");
									i.setStartDate(editStartDate);
									System.out.println("Start date successfully changed to " + i.getStartDate().toString());
								}
								else if(k == 2){
									Date editEndDate = IOHelper.getDateTimeAfter(new Date(), AppConstants.FORMAT_DATETIME_INPUT_NO_SECONDS, true,
									"Please enter date and time (" + AppConstants.FORMAT_DATETIME_INPUT_NO_SECONDS + ")[24 hour clock] : ",
									"There was an error parsing your input, please ensure you follow the provided format");
									i.setEndDate(editEndDate);
									System.out.println("End date successfully changed to " + i.getEndDate().toString());
								}
								DataHandler.saveData(i);
								break;
								case 4:
								//Update Promo Items
								System.out.println("1. Remove item from promotional set");
								System.out.println("2. Add item to promotional set");
								System.out.println("3. Back");
								k = IOHelper.getInt(1,3,true,2);
								if (k == 1){
									if(i.getPromoItems().isEmpty()){
										System.out.println("Promotional Set is empty");
										break;
									}
									System.out.println("Select promotional set item to remove");
									for(MenuItem item : i.getPromoItems()){
										System.out.println(Integer.toString(j) + ". " + item.getName());
										j++;
									}
									k = IOHelper.getInt(1,j-1,true,2);
									i.getPromoItems().remove(k-1);
									System.out.println("Item successfully removed from " + i.getName());
								}
								else if (k == 2){
									String add = IOHelper.getString(true,"Please enter the name of the menu item to be added: ", "Please enter a valid string");
									for(MenuItem ritem : Menu){
										if(ritem.getName().equals(add)){
											foundFlag2 = true;
											i.getPromoItems().add(ritem);
											System.out.println(ritem.getName() + " successfully added to promotion set");
											break;
										}
									}
									if (foundFlag2 = false){
										System.out.println("Item does not exist");
									}
								}
								DataHandler.saveData(i);	
								break;
							
							default:
								flag = false;
								break;
						}
					}
					DataHandler.saveData(i);
					break;
				}
			}
			if (foundFlag == false){
				System.out.println("Promotion Set not found");
			}
	}

	void deletePromo(){
		ArrayList<PromotionSet> PromotionSet = DataHandler.getAllData(new PromotionSet()); 
		logger.debug("There are " + PromotionSet.size() + " promotion sets.");
		if(PromotionSet.size()<1) {
			return;
		}
		String name = IOHelper.getString(true,"Please enter the name of promotional set you want to remove: ", "Please enter a valid string");
		for (PromotionSet i : PromotionSet) {
			if(i.getName().equals(name)) {
				DataHandler.removeData(i);
				System.out.println(name + " set successfully removed");
			}
		}

	}
}
