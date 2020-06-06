package com.titusgt.grocerystoreapplication;

import com.titusgt.grocerystoreapplication.enums.ProductType;
import com.titusgt.grocerystoreapplication.model.Product;
import com.titusgt.grocerystoreapplication.service.GroceryStoreService;
import java.math.BigDecimal;
import java.util.HashMap;

public class GroceryStoreCalculator {

	HashMap<Integer,Integer> frequency = new HashMap<>();

	public BigDecimal calculateItemPrice(Product product) {

		BigDecimal price = new BigDecimal(0);
		try {
			if (product != null) {
				if (product.getType().equals(ProductType.SALE.get()) && product.getFrequency() % 2 == 0){
					price = new BigDecimal(0);
				} else {
					price = product.getPrice();
				}
			}
		} catch (Exception ex) {
			return new BigDecimal(0);
		}

		return price;
	}

	public Product getProduct(String productCode, int productFrequency) {
		return new GroceryStoreService().getItem(Integer.parseInt(productCode), productFrequency);
	}

	public int getFrequency(int productCode) {
		if (frequency.containsKey(productCode)) {
			frequency.put(productCode, (frequency.get(productCode)+1));
		} else {
			frequency.put(productCode, 1);
		}
		return frequency.get(productCode);
	}
}
