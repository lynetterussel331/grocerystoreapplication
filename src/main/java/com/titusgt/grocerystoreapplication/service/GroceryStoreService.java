package com.titusgt.grocerystoreapplication.service;

import java.util.ArrayList;
import java.util.List;

import com.titusgt.grocerystoreapplication.model.GroceryStore;
import com.titusgt.grocerystoreapplication.utils.Constants;

public class GroceryStoreService {

	List<GroceryStore> alGroceryStore = new ArrayList<GroceryStore>();
	
	public GroceryStoreService() {
		
		GroceryStore gs = new GroceryStore();
		gs.setProductCode(1);
		gs.setProductName("PIATTOS CHIPS");
		gs.setProductType("PC");
		gs.setPrice(22.25);
		alGroceryStore.add(gs);
		
		gs = new GroceryStore();
		gs.setProductCode(2);
		gs.setProductName("AU HRVST OATML");
		gs.setProductType("PC");
		gs.setPrice(72.0);
		alGroceryStore.add(gs);
		
		gs = new GroceryStore();
		gs.setProductCode(3);
		gs.setProductName("FUJI APPLE");
		gs.setProductType("PC");
		gs.setPrice(40.0);
		alGroceryStore.add(gs);
		
		gs = new GroceryStore();
		gs.setProductCode(4);
		gs.setProductName("ANGELICA RICE");
		gs.setProductType("BULK");
		gs.setPrice(49.50);
		alGroceryStore.add(gs);
		
		gs = new GroceryStore();
		gs.setProductCode(5);
		gs.setProductName("ALL-PURPOSE FLR");
		gs.setProductType("BULK");
		gs.setPrice(25.0);
		alGroceryStore.add(gs);
		
		gs = new GroceryStore();
		gs.setProductCode(6);
		gs.setProductName("BROWN SUGAR");
		gs.setProductType("BULK");
		gs.setPrice(50.0);
		alGroceryStore.add(gs);
		
		gs = new GroceryStore();
		gs.setProductCode(7);
		gs.setProductName("SUNSILK SHMPOO");
		gs.setProductType("SALE");
		gs.setPrice(119.95);
		gs.setSaleType(Constants.BUY1_GET1);
		alGroceryStore.add(gs);
		
		gs = new GroceryStore();
		gs.setProductCode(8);
		gs.setProductName("CREAMSILK COND");
		gs.setProductType("SALE");
		gs.setPrice(129.95);
		gs.setSaleType(Constants.BUY1_GET1);
		alGroceryStore.add(gs);
		
		gs = new GroceryStore();
		gs.setProductCode(9);
		gs.setProductName("ARIEL DETERGENT");
		gs.setProductType("SALE");
		gs.setPrice(56.75);
		gs.setSaleType(Constants.BUY1_GET1);
		alGroceryStore.add(gs);
		
		gs = new GroceryStore();
		gs.setProductCode(10);
		gs.setProductName("TIDE DETERGENT");
		gs.setProductType("SALE");
		gs.setPrice(75.25);
		gs.setSaleType(Constants.BUY1_GET1);
		alGroceryStore.add(gs);
		
	}
	
	public GroceryStore getItem (int productCode, int productFrequency) {
		
		for (GroceryStore gs : alGroceryStore) {
			if ((int) gs.getProductCode() == productCode) {
				
				if (gs.getProductType().equals(Constants.SALE)) {
					gs.setProductFrequency(productFrequency);
					
					if (productFrequency%2 == 0) {
						gs.setPrice(0);
					}
				}
				
				return gs;
			}
		}
		
		return null;
	}
	
	public void displayItemsByProductType (String productType) {
		
		System.out.println("PRODUCT CODE\tPRODUCT NAME\t\tPRICE");
		System.out.println("---------------------------------------------");
		for (GroceryStore gs : alGroceryStore) {
			if (gs.getProductType().equals(productType)) {
				System.out.println(gs.getProductCode() + "\t\t" + gs.getProductName() + "\t\t" + gs.getPrice());
			}
		}
	}

}
