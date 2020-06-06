package com.titusgt.grocerystoreapplication;

import com.titusgt.grocerystoreapplication.enums.ProductType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.titusgt.grocerystoreapplication.model.Product;
import com.titusgt.grocerystoreapplication.view.Receipt;

public class CalculateTestMultiple {

	private Product product;
	private GroceryStoreCalculator calculator;
	private ProductPrice extract;
	private Random rand;
	private List<Product> groupOfProducts;
	
	@Before
	public void setUp() {
		calculator = new GroceryStoreCalculator();
		extract = new ProductPrice();
		rand = new Random();
		groupOfProducts = new ArrayList<>();
	}
	
	// POSITIVE SCENARIOS
	@Test
	public void calculateGroupOfProducts() {
		
		BigDecimal totalPrice = new BigDecimal(0);
		BigDecimal expectedPrice = new BigDecimal(0);
		
		int freqProduct1 = rand.nextInt(10) + 1; // PRODUCT FREQUENCY (PC)
		int freqProduct2 = rand.nextInt(10) + 1; // PRODUCT FREQUENCY (BULK)
		int freqProduct3 = rand.nextInt(10) + 1; // PRODUCT FREQUENCY (SALE)
		
		// BULK PRODUCTS
		for (int i = 1; i <= freqProduct2; i++) {
			
			int weight = rand.nextInt(10) + 1;
			String productCode2= (rand.nextInt(3) + 4) + ""; // PRODUCT FREQUENCY (BULK)
			
			product = calculator.getProduct(productCode2, 0);
			product.setWeight(weight);
			product.setPrice(product.getPrice().multiply(new BigDecimal(weight)));
			totalPrice = totalPrice.add(calculator.calculateItemPrice(product));
			expectedPrice = expectedPrice.add(extract.extractItemPrice(productCode2,
				ProductType.BULK.get()).multiply(BigDecimal.valueOf(weight)));
			groupOfProducts.add(product);
		}
		
		// SALE
		for (int i = 1; i <= freqProduct3; i++) {

			String productCode3 = (rand.nextInt(4) + 7) + ""; // PRODUCT FREQUENCY (SALE)
			int currentProductFreq = rand.nextInt(10) + 1; // CURRENT FREQUENCY

	    	BigDecimal extractedItemPrice = extract.extractItemPrice(productCode3, ProductType.SALE.get());

			for (int j = 1; j <= currentProductFreq; j++) {
	    		int productFrequency = calculator.getFrequency(Integer.parseInt(productCode3));
				product = calculator.getProduct(productCode3, productFrequency);
	    		product.setFrequency(productFrequency);
				totalPrice = totalPrice.add(calculator.calculateItemPrice(product));
				groupOfProducts.add(product);
			}
			
			// Computing expected Price
	    	expectedPrice = expectedPrice.add((currentProductFreq % 2 == 0 ?
				(extractedItemPrice.multiply(BigDecimal.valueOf(currentProductFreq)).divide(BigDecimal.valueOf(2)))
				: (extractedItemPrice.multiply(BigDecimal.valueOf(currentProductFreq/2+1)))));
		}
		
		// BY PIECE
		for (int i = 1; i <= freqProduct1; i++) {
			
			String productCode1 = (rand.nextInt(3) + 1) + ""; // PRODUCT FREQUENCY (PC)
			
			product = calculator.getProduct(productCode1, 0);
			totalPrice = totalPrice.add(calculator.calculateItemPrice(product));
			expectedPrice = expectedPrice.add(extract.extractItemPrice(productCode1, ProductType.PIECE.get()));
			groupOfProducts.add(product);
		}

		assert(expectedPrice.compareTo(totalPrice) == 0);

		System.out.println(new Receipt(60).printReceipt(groupOfProducts));
	}
}
