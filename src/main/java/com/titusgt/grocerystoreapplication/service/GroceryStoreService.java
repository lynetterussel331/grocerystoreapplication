package com.titusgt.grocerystoreapplication.service;

import com.titusgt.grocerystoreapplication.utils.ProductType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.titusgt.grocerystoreapplication.model.Product;
import org.apache.commons.lang3.StringUtils;

public class GroceryStoreService {

	List<Product> productList = new ArrayList<>();

	public GroceryStoreService() {
		productList.add(new Product(1, "PIATTOS CHIPS", "PC", new BigDecimal("22.25")));
		productList.add(new Product(2, "AU HRVST OATML", "PC", new BigDecimal("72.00")));
		productList.add(new Product(3, "FUJI APPLE", "PC", new BigDecimal("40.00")));
		productList.add(new Product(4, "ANGELICA RICE", "BULK", new BigDecimal("49.50")));
		productList.add(new Product(5, "ALL-PURPOSE FLR", "PC", new BigDecimal("25.00")));
		productList.add(new Product(6, "BROWN SUGAR", "BULK", new BigDecimal("50.00")));
		productList.add(new Product(7, "SUNSILK SHMPOO", "SALE", new BigDecimal("119.95"), ProductType.BUY1_GET1.get()));
		productList.add(new Product(8, "CREAMSILK COND", "SALE", new BigDecimal("129.95"), ProductType.BUY1_GET1.get()));
		productList.add(new Product(9, "ARIEL DETERGENT", "SALE", new BigDecimal("56.75"), ProductType.BUY1_GET1.get()));
		productList.add(new Product(10, "TIDE DETERGENT", "SALE", new BigDecimal("75.25"), ProductType.BUY1_GET1.get()));
	}
	
	public Product getItem(int productCode, int productFrequency) {
		for (Product product : productList) {
			if (product.getCode() == productCode) {
				if (product.getType().equals(ProductType.SALE.get())) {
					product.setFrequency(productFrequency);
					if (productFrequency % 2 == 0) {
						product.setPrice(new BigDecimal(0));
					}
				}
				return product;
			}
		}
		return null;
	}
	
	public void displayItemsByType(String type, int textLength) {
		List<String> columns = Arrays.asList("PRODUCT CODE", "PRODUCT NAME", "PRICE");
		int colLength = textLength / columns.size();
		columns.forEach(col -> System.out.print(StringUtils.rightPad(col, colLength, " ")));
		System.out.println("\n------------------------------------------------------------");
		for (Product gs : productList) {
			if (gs.getType().equals(type)) {
				List<String> values = Arrays.asList(Integer.toString(gs.getCode()), gs.getName(), String.valueOf(gs.getPrice()));
				values.forEach(val -> System.out.print(StringUtils.rightPad(val, colLength, " ")));
				System.out.println();
			}
		}
	}

}
