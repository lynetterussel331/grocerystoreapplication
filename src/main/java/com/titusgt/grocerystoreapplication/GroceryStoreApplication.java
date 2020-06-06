package com.titusgt.grocerystoreapplication;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.titusgt.grocerystoreapplication.utils.Constants;
import com.titusgt.grocerystoreapplication.utils.Validation;
import com.titusgt.grocerystoreapplication.view.Receipt;
import com.titusgt.grocerystoreapplication.model.Product;
import com.titusgt.grocerystoreapplication.service.GroceryStoreService;

public class GroceryStoreApplication {
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		List<Product> groupOfProducts = new ArrayList<Product>();
		
		GroceryStoreService gss = new GroceryStoreService();
		GroceryStoreCalculator gsc = new GroceryStoreCalculator();

		String productCode;
		String weight;
		
		DecimalFormat df = new DecimalFormat("#,##0.00");
		double totalPrice = 0.0;

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
					
					Product gs = gsc.getGroceryItem(productCode, productFrequency);
					Product gsBought = new Product();
					
					if (gs != null) {
						
						System.out.println("Product Name:\t" + gs.getName());
						
						gsBought.setCode(gs.getCode());
						gsBought.setName(gs.getName());
						gsBought.setType(gs.getType());
						gsBought.setPrice(gs.getPrice());
						
						if (gs.getType().equals(Constants.BULK)) {

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
							
						} else if (gs.getType().equals(Constants.SALE)) {
							
							gsBought.setSaleType(gs.getSaleType());
							gsBought.setFrequency(gs.getFrequency());
						}
						
						if (errMsg.isEmpty()) {
							totalPrice += gsc.calculateProduct(gsBought);
							
							groupOfProducts.add(gsBought);
							
							System.out.println("Subtotal: \t" + df.format(gsBought.getPrice()));
							System.out.println("Total Price: \t" + df.format(totalPrice));
						} else {
							gsc.setProductFrequency(gs.getCode(), -1);
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
