package com.titusgt.grocerystoreapplication;

import com.titusgt.grocerystoreapplication.enums.ProductType;
import com.titusgt.grocerystoreapplication.model.Product;
import com.titusgt.grocerystoreapplication.service.GroceryStoreService;
import com.titusgt.grocerystoreapplication.view.Receipt;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class CalculateTestMultiple {

	private Product product;
	private GroceryStoreCalculator calculator;
	private ProductPrice extract;
	private List<Product> groupOfProducts;
	private GroceryStoreService service = new GroceryStoreService();

	@Before
	public void setUp() {
		calculator = new GroceryStoreCalculator();
		extract = new ProductPrice();
		groupOfProducts = new ArrayList<>();
	}

	// POSITIVE SCENARIOS
	@Test
	public void calculateGroupOfProducts() {

		BigDecimal totalPrice = new BigDecimal(0);
		BigDecimal expectedPrice = new BigDecimal(0);

		// BULK PRODUCTS
		for (int i = 1; i <= 3; i++) {

			int weight = 4;
			int code = 5;

			product = service.getProduct(code , 0);
			product.setWeight(weight);
			product.setPrice(product.getPrice().multiply(new BigDecimal(weight)));
			totalPrice = totalPrice.add(calculator.calculateItemPrice(product));
			expectedPrice = expectedPrice.add(extract.extractItemPrice(code ,
				ProductType.BULK.get()).multiply(BigDecimal.valueOf(weight)));
			groupOfProducts.add(product);
		}

		// SALE

		int saleCode = 8;
		int saleFrequency = 2;

		BigDecimal extractedItemPrice = extract.extractItemPrice(saleCode, ProductType.SALE.get());

		for (int i = 1; i <= saleFrequency; i++) {
			product = service.getProduct(saleCode, i);
			product.setFrequency(i);
			totalPrice = totalPrice.add(calculator.calculateItemPrice(product));
			groupOfProducts.add(product);
		}

		// Computing expected Price
		expectedPrice = expectedPrice.add((saleFrequency % 2 == 0 ?
			(extractedItemPrice.multiply(BigDecimal.valueOf(saleFrequency)).divide(BigDecimal.valueOf(2)))
			: (extractedItemPrice.multiply(BigDecimal.valueOf(saleFrequency/2+1)))));

		// BY PIECE
		for (int i = 1; i <= 2; i++) {

			int code = 2;

			product = service.getProduct(code, 0);
			totalPrice = totalPrice.add(calculator.calculateItemPrice(product));
			expectedPrice = expectedPrice.add(extract.extractItemPrice(code, ProductType.PIECE.get()));
			groupOfProducts.add(product);
		}

		assert(expectedPrice.compareTo(totalPrice) == 0);

		System.out.println(new Receipt(60).printReceipt(groupOfProducts));
	}
}
