package com.titusgt.grocerystoreapplication;

import com.titusgt.grocerystoreapplication.model.Product;
import com.titusgt.grocerystoreapplication.service.GroceryStoreService;
import java.math.BigDecimal;

public class ProductPrice {

	public BigDecimal extractItemPrice(String productCode, String productType) {

		int productFrequency = new GroceryStoreCalculator().getProductFrequency(Integer.parseInt(productCode));
		Product gs = new GroceryStoreService().getItem(Integer.parseInt(productCode), productFrequency);
		BigDecimal itemPrice = new BigDecimal(0);
		
		if (gs.getType().equals(productType))
			itemPrice = gs.getPrice();
		
		return itemPrice;
	}
	
}
