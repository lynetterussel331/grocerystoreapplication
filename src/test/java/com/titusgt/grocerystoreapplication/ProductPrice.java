package com.titusgt.grocerystoreapplication;

import com.titusgt.grocerystoreapplication.model.Product;
import com.titusgt.grocerystoreapplication.service.GroceryStoreService;
import java.math.BigDecimal;

public class ProductPrice {

	public BigDecimal extractItemPrice(String productCode, String productType) {

		int productFrequency = new GroceryStoreCalculator().getFrequency(Integer.parseInt(productCode));
		Product product = new GroceryStoreService().getItem(Integer.parseInt(productCode), productFrequency);
		BigDecimal itemPrice = new BigDecimal(0);
		
		if (product.getType().equals(productType))
			itemPrice = product.getPrice();
		
		return itemPrice;
	}
	
}
