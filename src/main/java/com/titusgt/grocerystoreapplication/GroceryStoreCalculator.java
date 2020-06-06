package com.titusgt.grocerystoreapplication;

import com.titusgt.grocerystoreapplication.utils.ProductType;
import java.math.BigDecimal;
import java.util.HashMap;

import com.titusgt.grocerystoreapplication.model.Product;
import com.titusgt.grocerystoreapplication.service.GroceryStoreService;

public class GroceryStoreCalculator {

	HashMap<Integer,Integer> productFrequency = new HashMap<Integer,Integer>();
	
	public BigDecimal calculateProduct(Product gs) {
		
		BigDecimal totalPrice = new BigDecimal(0);
		
		try {
			if (gs != null) {
				
				if (gs.getType().equals(ProductType.SALE.get()) && gs.getFrequency()%2 == 0){
					totalPrice = new BigDecimal(0);
				} else {
					totalPrice = gs.getPrice();
				}
			}
		} catch (Exception ex) {
			return new BigDecimal(0);
		}
	
		return totalPrice;
	}
	
	public Product getGroceryItem(String productCode, int productFrequency) {
		
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
