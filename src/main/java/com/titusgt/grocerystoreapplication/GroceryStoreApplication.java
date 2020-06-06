package com.titusgt.grocerystoreapplication;

import com.titusgt.grocerystoreapplication.enums.ProductType;
import com.titusgt.grocerystoreapplication.view.GroceryStoreCashier;
import com.titusgt.grocerystoreapplication.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GroceryStoreApplication {

	public static void main(String[] args) {

		GroceryStoreCalculator calculator = new GroceryStoreCalculator();
		List<Product> productList = new ArrayList<>();

		GroceryStoreCashier.displayProducts(" SINGLE ITEMS ", ProductType.PIECE.get(), 60);
		GroceryStoreCashier.displayProducts(" BULK ITEMS ", ProductType.BULK.get(), 60);
		GroceryStoreCashier.displayProducts(" SPECIAL PROMOTIONS ", ProductType.SALE.get(), 60);

		Scanner scanner = new Scanner(System.in);

		try {
			String productCode;
			BigDecimal totalPrice = new BigDecimal(0);
			do {
				System.out.print("Scan Product Code: ");
				productCode = scanner.nextLine();
				GroceryStoreCashier.processItems(productCode, calculator, scanner, totalPrice, productList);
			} while (!productCode.isEmpty());

			GroceryStoreCashier.printReceipt(productList);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			scanner.close();
		}
	}
}
