package com.titusgt.grocerystoreapplication.service;

import com.titusgt.grocerystoreapplication.enums.ProductType;
import com.titusgt.grocerystoreapplication.model.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class GroceryStoreService {

	List<Product> productList = new ArrayList<>();

	public GroceryStoreService() {
		productList.add(new Product(1, "PIATTOS CHIPS", ProductType.PIECE.get(), new BigDecimal("22.25")));
		productList.add(new Product(2, "AU HRVST OATML", ProductType.PIECE.get(), new BigDecimal("72.00")));
		productList.add(new Product(3, "FUJI APPLE", ProductType.PIECE.get(), new BigDecimal("40.00")));
		productList.add(new Product(4, "ANGELICA RICE", ProductType.BULK.get(), new BigDecimal("49.50")));
		productList.add(new Product(5, "ALL-PURPOSE FLR", ProductType.PIECE.get(), new BigDecimal("25.00")));
		productList.add(new Product(6, "BROWN SUGAR", ProductType.BULK.get(), new BigDecimal("50.00")));
		productList.add(new Product(7, "SUNSILK SHMPOO", ProductType.SALE.get(), new BigDecimal("119.95")));
		productList.add(new Product(8, "CREAMSILK COND", ProductType.SALE.get(), new BigDecimal("129.95")));
		productList.add(new Product(9, "ARIEL DETERGENT", ProductType.SALE.get(), new BigDecimal("56.75")));
		productList.add(new Product(10, "TIDE DETERGENT", ProductType.SALE.get(), new BigDecimal("75.25")));
	}
	
	public Product getItem(int productCode, int productFrequency) {

		Product product = productList.stream()
			.filter(prod -> prod.getCode() == productCode)
			.findAny().orElse(null);

		if (product != null) {
			if (product.getType().equals(ProductType.SALE.get())) {
				product.setFrequency(productFrequency);
				if (productFrequency % 2 == 0) {
					product.setPrice(new BigDecimal(0));
				}
			}
			return product;
		}
		return null;
	}
	
	public void displayItemsByType(String type, int textLength) {

		List<String> columns = Arrays.asList("PRODUCT CODE", "PRODUCT NAME", "PRICE");
		int colLength = textLength / columns.size();
		columns.forEach(col -> System.out.print(StringUtils.rightPad(col, colLength, " ")));

		System.out.print("\n" + StringUtils.rightPad("", textLength, "-"));

		for (Product product : productList) {
			if (product.getType().equals(type)) {
				List<String> values = Arrays.asList(
					Integer.toString(product.getCode()), product.getName(), String.valueOf(product.getPrice()));
				System.out.println();
				values.forEach(val -> System.out.print(StringUtils.rightPad(val, colLength, " ")));
			}
		}
	}

}
