package com.titusgt.grocerystoreapplication;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.titusgt.grocerystoreapplication.utils.Constants;
import com.titusgt.grocerystoreapplication.utils.Validation;
import com.titusgt.grocerystoreapplication.view.Receipt;
import com.titusgt.grocerystoreapplication.model.GroceryStore;
import com.titusgt.grocerystoreapplication.service.GroceryStoreService;

public class GroceryStoreApplication {
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		List<GroceryStore> groupOfProducts = new ArrayList<GroceryStore>();
		
		GroceryStoreService gss = new GroceryStoreService();
		GroceryStoreCalculator gsc = new GroceryStoreCalculator();

		String productCode;
		String weight;
		
		DecimalFormat df = new DecimalFormat("#,##0.00");
		double totalPrice = 0.0;

		// DISPLAY LIST OF SPECIAL PROMOTIONS
		System.out.println("============ SPECIAL PROMOTIONS =============");
		gss.displayItemsByProductType(Constants.SALE);
		System.out.println("=============================================");
		
		try {
			do {
				System.out.print("Scan Product Code: ");
				productCode = scanner.nextLine();

				String errMsg = "";
				
				if (Validation.isNumeric(productCode)) {

					int productFrequency = gsc.getProductFrequency(Integer.parseInt(productCode));
					
					GroceryStore gs = gsc.getGroceryItem(productCode, productFrequency);
					GroceryStore gsBought = new GroceryStore();
					
					if (gs != null) {
						
						System.out.println("Product Name:\t" + gs.getProductName());
						
						gsBought.setProductCode(gs.getProductCode());
						gsBought.setProductName(gs.getProductName());
						gsBought.setProductType(gs.getProductType());
						gsBought.setPrice(gs.getPrice());
						
						if (gs.getProductType().equals(Constants.BULK)) {

							System.out.println("Price: \t\t" + df.format(gs.getPrice()) + "/kg");
							System.out.print("Weight: \t");
							weight = scanner.nextLine();
							
							if (Validation.isDecimal(weight)) {
								double dWeight = Double.parseDouble(weight);
								gsBought.setWeight(dWeight);
								gsBought.setPrice(gs.getPrice() * dWeight);
							} else {
								errMsg = "Invalid weight!";
							}
							
						} else if (gs.getProductType().equals(Constants.SALE)) {
							
							gsBought.setSaleType(gs.getSaleType());
							gsBought.setProductFrequency(gs.getProductFrequency());
						}
						
						if (errMsg.isEmpty()) {
							totalPrice += gsc.calculateProduct(gsBought);
							
							groupOfProducts.add(gsBought);
							
							System.out.println("Subtotal: \t" + df.format(gsBought.getPrice()));
							System.out.println("Total Price: \t" + df.format(totalPrice));
						} else {
							gsc.setProductFrequency(gs.getProductCode(), -1);
						}
						
					} else {
						errMsg = "Entered Product is not existing!";
					} 
					
				} else if (!productCode.isEmpty()) {
					errMsg = "Invalid Product Code!";
				}

				System.out.println(errMsg);
	
			} while (!productCode.isEmpty());
			
			System.out.println(new Receipt().printReceipt(groupOfProducts));
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			scanner.close();
		}		
	}
}
