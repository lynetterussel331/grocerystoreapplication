package com.titusgt.grocerystoreapplication;

import static org.junit.Assert.*;

import com.titusgt.grocerystoreapplication.enums.ProductType;
import java.math.BigDecimal;
import java.util.Random;

import org.junit.Test;

import com.titusgt.grocerystoreapplication.model.Product;
import com.titusgt.grocerystoreapplication.utils.Validation;

public class CalculateTestSingle
{

	private Product product = new Product();
	private GroceryStoreCalculator calculator = new GroceryStoreCalculator();
	private ProductPrice extract = new ProductPrice();
	private Random rand = new Random();

	@Test
    public void calculateByPc() {
	   	
		String product = (rand.nextInt(3) + 1) + ""; // PRODUCT CODE: 1-3
    	
		this.product = calculator.getProduct(product, 0);
    	BigDecimal totalPrice = calculator.calculateItemPrice(this.product);
		BigDecimal expectedPrice = extract.extractItemPrice(product, ProductType.PIECE.get());

    	assert(expectedPrice.compareTo(totalPrice) == 0);
    }
	
	@Test
    public void calculateBulk() {
    	
		String product = (rand.nextInt(3) + 4) + ""; // PRODUCT CODE: 4-6
		int weight = rand.nextInt(10) + 1;

		this.product = calculator.getProduct(product, 0);
		this.product.setWeight(weight);
		this.product.setPrice(this.product.getPrice().multiply(BigDecimal.valueOf(weight)));

		BigDecimal totalPrice = calculator.calculateItemPrice(this.product);
		BigDecimal expectedPrice = extract.extractItemPrice(product, ProductType.BULK.get())
			.multiply(BigDecimal.valueOf(weight));

    	assert(expectedPrice.compareTo(totalPrice) == 0);
    }
	
	@Test
    public void calculateSale() {
    	
		String product = (rand.nextInt(4) + 7) + ""; // PRODUCT CODE: 7-10
    	int freq = rand.nextInt(10) + 1;

    	BigDecimal totalPrice = new BigDecimal(0);
		BigDecimal extractedItemPrice = new BigDecimal(0);
		BigDecimal expectedPrice = new BigDecimal(0);
    	
    	for (int i = 1; i <= freq; i++) {
    		int productFrequency = calculator.getFrequency(Integer.parseInt(product));
    		this.product = calculator.getProduct(product, productFrequency);
    		this.product.setFrequency(productFrequency);
    		totalPrice = totalPrice.add(calculator.calculateItemPrice(this.product));
    	}
    	
    	// Computing expected Price
    	extractedItemPrice = extract.extractItemPrice(product, ProductType.SALE.get());
    	if (freq % 2 == 0) {
			expectedPrice = extractedItemPrice.multiply(BigDecimal.valueOf(freq / 2));
		} else {
			expectedPrice = extractedItemPrice.multiply(BigDecimal.valueOf(freq / 2 + 1));
		}

    	assert(expectedPrice.compareTo(totalPrice) == 0);
    }
	
	// NEGATIVE SCENARIOS
	
	@Test
    public void calculatePcNotExisting() {
		
		String product = (rand.nextInt(20) + 4) + ""; // PRODUCT CODE NOT 1-3
		
		this.product = calculator.getProduct(product, 0);
		
		if (this.product != null && !this.product.getType().equals(ProductType.PIECE.get())) {
			this.product = null;
		}
		
    	assertNull(this.product);
	}
	
	@Test
    public void calculateBulkNotExisting() {
		
		String product = (rand.nextInt(20) + 7) + ""; // PRODUCT CODE NOT 4-6
		
		this.product = calculator.getProduct(product, 0);
		
		if (this.product != null && !this.product.getType().equals(ProductType.BULK.get())) {
			this.product = null;
		}
		
    	assertNull(this.product);
	}
	
	@Test
    public void calculateSaleNotExisting() {

		String product = (rand.nextInt(20) + 11) + ""; // PRODUCT CODE NOT 7-10

		this.product = calculator.getProduct(product, 0);
		if (this.product != null && !this.product.getType().equals(ProductType.SALE.get())) {
			this.product = null;
		}

    	assertNull(this.product);
	}
	
	@Test
    public void calculatePcInvalid() {

		String product = (char)((rand.nextInt(26) + 1) + 'z') + ""; // CHARACTERS
		boolean isValid = Validation.isNumeric(product);
		
		assertFalse(isValid);
	}
	
	@Test
    public void calculateBulkInvalid() {
		
		String product = (char)((rand.nextInt(26) + 1) + 'z') + ""; // CHARACTERS
		boolean isValid = Validation.isNumeric(product);
		
		assertFalse(isValid);
	}
	
	@Test
    public void calculateSaleInvalid() {
		
		String product = (char)((rand.nextInt(26) + 1) + 'z') + ""; // CHARACTERS
		boolean isValid = Validation.isNumeric(product);
		
    	assertFalse(isValid);
	}
}
