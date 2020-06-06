package com.titusgt.grocerystoreapplication;

import com.titusgt.grocerystoreapplication.utils.ProductType;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.titusgt.grocerystoreapplication.utils.Validation;
import com.titusgt.grocerystoreapplication.view.Receipt;
import com.titusgt.grocerystoreapplication.model.Product;
import com.titusgt.grocerystoreapplication.service.GroceryStoreService;
import org.apache.commons.lang3.StringUtils;

public class GroceryStoreApplication {
	
	public static void main(String[] args) {
		GroceryStoreCalculator calculator = new GroceryStoreCalculator();
		List<Product> productList = new ArrayList<>();

		DecimalFormat df = new DecimalFormat("#,##0.00");
		Scanner scanner = new Scanner(System.in);

		BigDecimal totalPrice = new BigDecimal(0);

		displayProducts(" SPECIAL PROMOTIONS ", ProductType.SALE.get(), 60);

		try {
			String productCode;
			do {
				System.out.print("Scan Product Code: ");
				productCode = scanner.nextLine();

				String errMsg = "";
				
				if (Validation.isNumeric(productCode)) {

					int productFrequency = calculator.getProductFrequency(Integer.parseInt(productCode));
					
					Product gs = calculator.getGroceryItem(productCode, productFrequency);
					Product order = new Product();
					
					if (gs != null) {
						
						System.out.println("Product Name:\t" + gs.getName());
						
						order.setCode(gs.getCode());
						order.setName(gs.getName());
						order.setType(gs.getType());
						order.setPrice(gs.getPrice());
						
						if (gs.getType().equals(ProductType.BULK.get())) {

							System.out.println("Price: \t\t" + df.format(gs.getPrice()) + "/kg");
							System.out.print("Weight: \t");
							String weight = scanner.nextLine();
							
							if (Validation.isDecimal(weight)) {
								double dWeight = Double.parseDouble(weight);
								order.setWeight(dWeight);
								order.setPrice(gs.getPrice().multiply(BigDecimal.valueOf(dWeight)));
							} else {
								errMsg = "Invalid weight!";
							}
							
						} else if (gs.getType().equals(ProductType.SALE.get())) {
							
							order.setSaleType(gs.getSaleType());
							order.setFrequency(gs.getFrequency());
						}
						
						if (errMsg.isEmpty()) {
							totalPrice = totalPrice.add(calculator.calculateProduct(order));
							
							productList.add(order);
							
							System.out.println("Subtotal: \t" + df.format(order.getPrice()));
							System.out.println("Total Price: \t" + df.format(totalPrice));
						} else {
							calculator.setProductFrequency(gs.getCode(), -1);
						}
						
					} else {
						errMsg = "Entered Product is not existing!";
					} 
					
				} else if (!productCode.isEmpty()) {
					errMsg = "Invalid Product Code!";
				}

				System.out.println(errMsg);
	
			} while (!productCode.isEmpty());
			
			System.out.println(new Receipt(60).printReceipt(productList));

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			scanner.close();
		}		
	}

	private static void displayProducts(String header, String type, int textLength) {
		System.out.println(StringUtils.center(header, textLength, "="));
		new GroceryStoreService().displayItemsByType(type, textLength);
		System.out.println(StringUtils.center("", textLength, "="));
	}
}
