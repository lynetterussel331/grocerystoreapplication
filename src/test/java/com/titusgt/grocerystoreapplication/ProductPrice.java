package com.titusgt.grocerystoreapplication;

import com.titusgt.grocerystoreapplication.model.GroceryStore;
import com.titusgt.grocerystoreapplication.service.GroceryStoreService;

public class ProductPrice {

	public double extractItemPrice(String productCode, String productType) {

		int productFrequency = new GroceryStoreCalculator().getProductFrequency(Integer.parseInt(productCode));
		GroceryStore gs = new GroceryStoreService().getItem(Integer.parseInt(productCode), productFrequency);
		double itemPrice = 0;
		
		if (gs.getProductType().equals(productType))
			itemPrice = gs.getPrice();
		
		return itemPrice;
	}
	
}
