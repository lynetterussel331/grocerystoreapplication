package com.titusgt.grocerystoreapplication;

import com.titusgt.grocerystoreapplication.model.Product;
import com.titusgt.grocerystoreapplication.service.GroceryStoreService;

public class ProductPrice {

	public double extractItemPrice(String productCode, String productType) {

		int productFrequency = new GroceryStoreCalculator().getProductFrequency(Integer.parseInt(productCode));
		Product gs = new GroceryStoreService().getItem(Integer.parseInt(productCode), productFrequency);
		double itemPrice = 0;
		
		if (gs.getType().equals(productType))
			itemPrice = gs.getPrice();
		
		return itemPrice;
	}
	
}
