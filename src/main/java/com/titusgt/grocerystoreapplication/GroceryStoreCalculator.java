package com.titusgt.grocerystoreapplication;

import java.util.HashMap;

import com.titusgt.grocerystoreapplication.model.GroceryStore;
import com.titusgt.grocerystoreapplication.service.GroceryStoreService;
import com.titusgt.grocerystoreapplication.utils.Constants;

public class GroceryStoreCalculator {

	HashMap<Integer,Integer> productFrequency = new HashMap<Integer,Integer>();
	
	public double calculateProduct(GroceryStore gs) {
		
		double totalPrice = 0;
		
		try {
			if (gs != null) {
				
				if (gs.getProductType().equals(Constants.SALE) && gs.getProductFrequency()%2 == 0){
					totalPrice = 0;
				} else {
					totalPrice = gs.getPrice();
				}
			}
		} catch (Exception ex) {
			return 0;
		}
	
		return totalPrice;
	}
	
	public GroceryStore getGroceryItem(String productCode, int productFrequency) {
		
		return new GroceryStoreService().getItem(Integer.parseInt(productCode), productFrequency);
	}
	
	public int getProductFrequency(int productCode) {
		
		if (productFrequency.containsKey(productCode)) {
			productFrequency.put(productCode, (productFrequency.get(productCode)+1));
		}
		else {
			productFrequency.put(productCode, 1);
		}
		
		return productFrequency.get(productCode);
	}
	
	public void setProductFrequency(int productCode, int num) {
		
		if (productFrequency.containsKey(productCode)) {
			productFrequency.put(productCode, (productFrequency.get(productCode)+num));
		}
	}
}