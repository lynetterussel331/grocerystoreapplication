package com.titusgt.grocerystoreapplication;

import static org.junit.Assert.assertNull;

import com.titusgt.grocerystoreapplication.enums.ProductType;
import com.titusgt.grocerystoreapplication.model.Product;
import com.titusgt.grocerystoreapplication.service.GroceryStoreService;
import java.math.BigDecimal;
import java.util.Random;
import org.junit.Test;

public class CalculateTestSingle {

	private Product product = new Product();
	private GroceryStoreCalculator calculator = new GroceryStoreCalculator();
	private ProductPrice extract = new ProductPrice();
	private GroceryStoreService service = new GroceryStoreService();

	@Test
    public void calculateByPc() {

		int code = 2;

		this.product = service.getProduct(code, 0);
    	BigDecimal totalPrice = calculator.calculateItemPrice(this.product);
		BigDecimal expectedPrice = extract.extractItemPrice(code, ProductType.PIECE.get());

    	assert(expectedPrice.compareTo(totalPrice) == 0);
    }

	@Test
    public void calculateBulk() {

		int code = 6;
		double weight = 5;

		this.product = service.getProduct(code, 0);
		this.product.setWeight(weight);
		this.product.setPrice(this.product.getPrice().multiply(BigDecimal.valueOf(weight)));

		BigDecimal totalPrice = calculator.calculateItemPrice(this.product);
		BigDecimal expectedPrice = extract.extractItemPrice(code, ProductType.BULK.get())
			.multiply(BigDecimal.valueOf(weight));

    	assert(expectedPrice.compareTo(totalPrice) == 0);
    }

	@Test
    public void calculateSale() {

		int code = 10;
    	int frequency = 2;

    	BigDecimal totalPrice = new BigDecimal(0);
		BigDecimal extractedItemPrice;
		BigDecimal expectedPrice;

    	for (int i = 1; i <= frequency; i++) {
    		this.product = service.getProduct(code, i);
			totalPrice = totalPrice.add(calculator.calculateItemPrice(this.product));
    	}

    	// Computing expected Price
    	extractedItemPrice = extract.extractItemPrice(code, ProductType.SALE.get());
    	if (frequency % 2 == 0) {
			expectedPrice = extractedItemPrice.multiply(BigDecimal.valueOf(frequency / 2));
		} else {
			expectedPrice = extractedItemPrice.multiply(BigDecimal.valueOf(frequency / 2 + 1));
		}

    	System.out.println("expectedPrice: " + expectedPrice);
		System.out.println("totalPrice: " + totalPrice);

    	assert(expectedPrice.compareTo(totalPrice) == 0);
    }
}
