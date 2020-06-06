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

	static DecimalFormat df = new DecimalFormat("#,##0.00");

	public static void main(String[] args) {

		GroceryStoreCalculator calculator = new GroceryStoreCalculator();
		List<Product> productList = new ArrayList<>();
		Scanner scanner = new Scanner(System.in);

		displayProducts(" SPECIAL PROMOTIONS ", ProductType.SALE.get(), 60);

		try {
			String productCode;
			BigDecimal totalPrice = new BigDecimal(0);
			do {
				System.out.print("Scan Product Code: ");
				productCode = scanner.nextLine();
				processItems(productCode, calculator, scanner, totalPrice, productList);
			} while (!productCode.isEmpty());

			printReceipt(productList);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			scanner.close();
		}		
	}

	private static void printReceipt(List<Product> productList) {
		if (productList.size() > 0) {
			System.out.println(new Receipt(60).printReceipt(productList));
		} else {
			System.out.println("NO ACTUAL ORDER!");
		}
	}

	private static void displayProducts(String header, String type, int textLength) {
		System.out.println(StringUtils.center(header, textLength, "="));
		new GroceryStoreService().displayItemsByType(type, textLength);
		System.out.println(StringUtils.center("", textLength, "="));
	}

	private static void printPrice(Product product, BigDecimal totalPrice) {
		System.out.println("Subtotal: \t" + df.format(product.getPrice()));
		System.out.println("Total Price: \t" + df.format(totalPrice));
	}

	private static void processItems(String productCode, GroceryStoreCalculator calculator,
		Scanner scanner, BigDecimal totalPrice, List<Product> productList) {
		if (Validation.isNumeric(productCode)) {
			try {
				Product product = inputItems(productCode, calculator, scanner);
				totalPrice = totalPrice.add(calculator.calculateProduct(product));
				productList.add(product);
				printPrice(product, totalPrice);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} else if (!productCode.isEmpty()) {
			System.out.println("Invalid Product Code!");
		}
	}

	private static Product inputItems(String productCode, GroceryStoreCalculator calculator, Scanner scanner) throws Exception {

		int productFrequency = calculator.getProductFrequency(Integer.parseInt(productCode));
		Product product = calculator.getGroceryItem(productCode, productFrequency);

		if (product != null) {
			System.out.println("Product Name:\t" + product.getName());

			if (product.getType().equals(ProductType.BULK.get())) {
				processBulkItems(product, scanner);
			}
		} else {
			throw new Exception("Entered Product is not existing!");
		}

		return product;
	}

	private static void processBulkItems(Product product, Scanner scanner) throws Exception {
		System.out.println("Price: \t\t" + df.format(product.getPrice()) + "/kg");
		System.out.print("Weight: \t");
		String inputWeight = scanner.nextLine();

		if (Validation.isDecimal(inputWeight)) {
			double weight = Double.parseDouble(inputWeight);
			product.setWeight(weight);
			product.setPrice(product.getPrice().multiply(BigDecimal.valueOf(weight)));
		} else {
			throw new Exception("Invalid weight!");
		}
	}
}
