package com.titusgt.grocerystoreapplication;

import com.titusgt.grocerystoreapplication.model.Product;
import com.titusgt.grocerystoreapplication.service.GroceryStoreService;
import java.math.BigDecimal;

public class ProductPrice {

	public BigDecimal extractItemPrice(int code, String type) {

		int frequency = new GroceryStoreCalculator().getFrequency(code);
		Product product = new GroceryStoreService().getProduct(code, frequency);
		BigDecimal itemPrice = new BigDecimal(0);
		
		if (product.getType().equals(type))
			itemPrice = product.getPrice();
		
		return itemPrice;
	}
	
}
