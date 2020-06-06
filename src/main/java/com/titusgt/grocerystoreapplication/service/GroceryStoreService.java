package com.titusgt.grocerystoreapplication.service;

import java.util.ArrayList;
import java.util.List;

import com.titusgt.grocerystoreapplication.model.Product;
import com.titusgt.grocerystoreapplication.utils.Constants;

public class GroceryStoreService {

	List<Product> productList = new ArrayList<>();

	public GroceryStoreService() {

		Product product1 = new Product(1, "PIATTOS CHIPS", "PC", 22.25);
		Product product2 = new Product(2, "AU HRVST OATML", "PC", 72.00);
		Product product3 = new Product(3, "FUJI APPLE", "PC", 40.00);
		Product product4 = new Product(4, "ANGELICA RICE", "BULK", 49.50);
		Product product5 = new Product(5, "ALL-PURPOSE FLR", "PC", 25.00);
		Product product6 = new Product(6, "BROWN SUGAR", "BULK", 50.00);
		Product product7 = new Product(7, "SUNSILK SHMPOO", "SALE", 119.95, Constants.BUY1_GET1);
		Product product8 = new Product(8, "CREAMSILK COND", "SALE", 129.95, Constants.BUY1_GET1);
		Product product9 = new Product(9, "ARIEL DETERGENT", "SALE", 56.75, Constants.BUY1_GET1);
		Product product10 = new Product(10, "TIDE DETERGENT", "SALE", 75.25, Constants.BUY1_GET1);

		productList.add(product1);
		productList.add(product2);
		productList.add(product3);
		productList.add(product4);
		productList.add(product5);
		productList.add(product6);
		productList.add(product7);
		productList.add(product8);
		productList.add(product9);
		productList.add(product10);
	}
	
	public Product getItem(int productCode, int productFrequency) {
		for (Product product : productList) {
			if (product.getCode() == productCode) {
				if (product.getType().equals(Constants.SALE)) {
					product.setFrequency(productFrequency);
					if (productFrequency % 2 == 0) {
						product.setPrice(0);
					}
				}
				return product;
			}
		}
		return null;
	}
	
	public void displayItemsByProductType(String productType) {
		System.out.println("PRODUCT CODE\tPRODUCT NAME\t\tPRICE");
		System.out.println("---------------------------------------------");
		for (Product gs : productList) {
			if (gs.getType().equals(productType)) {
				System.out.println(gs.getCode() + "\t\t" + gs.getName() + "\t\t" + gs.getPrice());
			}
		}
	}

}
