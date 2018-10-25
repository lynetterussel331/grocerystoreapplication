package com.titusgt.grocerystoreapplication.view;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collections;
import java.util.List;

import com.titusgt.grocerystoreapplication.utils.Constants;
import com.titusgt.grocerystoreapplication.model.GroceryStore;

public class Receipt {

	public String printReceipt(List<GroceryStore> productList) {

		Collections.sort(productList,GroceryStore.COMPARE_BY_PRODUCTNAME);
		
		System.out.println("Printing receipt...");
		
		DecimalFormat df = new DecimalFormat("#,##0.00");
		double totalPrice = 0.0;
		
		DateTimeFormatter shortDt = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
		
		StringBuilder sb = new StringBuilder("=================== SM Supermarket ===================\n");
		sb.append("================== " + shortDt.format(LocalDateTime.now()) + " ==================\n\n");
		
		sb.append("\t\t\t\t\t\tPHP\n");
		
		for (GroceryStore gs : productList) {
			sb.append(gs.getProductName() + "\t\t\t\t\t" + df.format(gs.getPrice()) + "\n");
			
			if (gs.getProductType().equals(Constants.BULK)) {
				sb.append("- 1 @ " + df.format(gs.getPrice() / gs.getWeight()) + "\tX " + gs.getWeight() + "KG\n");
			} else if (gs.getProductType().equals(Constants.SALE) && gs.getPrice() == 0) {
				sb.append("- " + gs.getSaleType() + "\n");
			}
			
			totalPrice += gs.getPrice();
		}
		
		sb.append("\n-------------------- NOTHING FOLLOWS -----------------\n");
		
		sb.append("\n\t\tTotal Price: \t\t\t" + df.format(totalPrice) + "\n\n");
		
		sb.append("======================================================\n");
		
		return sb.toString();
	}
	
}
