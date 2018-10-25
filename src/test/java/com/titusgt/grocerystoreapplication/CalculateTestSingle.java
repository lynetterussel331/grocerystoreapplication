package com.titusgt.grocerystoreapplication;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

import com.titusgt.grocerystoreapplication.model.GroceryStore;
import com.titusgt.grocerystoreapplication.utils.Constants;
import com.titusgt.grocerystoreapplication.utils.Utils;
import com.titusgt.grocerystoreapplication.utils.Validation;

public class CalculateTestSingle
{

	private GroceryStore gs = new GroceryStore();
	private GroceryStoreCalculator gsc = new GroceryStoreCalculator();
	private ProductPrice extract = new ProductPrice();
	private Random rand = new Random();

	@Test
    public void calculateByPc() {
	   	
		String product = (rand.nextInt(3) + 1) + ""; // PRODUCT CODE: 1-3
    	
		gs = gsc.getGroceryItem(product, 0);
    	double totalPrice = gsc.calculateProduct(gs);
    	double expectedPrice = extract.extractItemPrice(product, Constants.PIECE);
    	
    	expectedPrice = Utils.round(expectedPrice,2);
		totalPrice = Utils.round(totalPrice,2);
		
    	assertEquals(expectedPrice, totalPrice, 0);
    }
	
	@Test
    public void calculateBulk() {
    	
		String product = (rand.nextInt(3) + 4) + ""; // PRODUCT CODE: 4-6
		int weight = rand.nextInt(10) + 1;

		gs = gsc.getGroceryItem(product, 0);
		gs.setWeight(weight);
		gs.setPrice(gs.getPrice() * weight);
		
    	double totalPrice = gsc.calculateProduct(gs);
    	double expectedPrice = extract.extractItemPrice(product, Constants.BULK) * weight;

    	expectedPrice = Utils.round(expectedPrice,2);
		totalPrice = Utils.round(totalPrice,2);
		
    	assertEquals(expectedPrice, totalPrice, 0);
    }
	
	@Test
    public void calculateSale() {
    	
		String product = (rand.nextInt(4) + 7) + ""; // PRODUCT CODE: 7-10
    	int freq = rand.nextInt(10) + 1;

    	double totalPrice = 0;
    	double extractedItemPrice = 0;
    	double expectedPrice = 0;
    	
    	for (int i = 1; i <= freq; i++) {
    		int productFrequency = gsc.getProductFrequency(Integer.parseInt(product));
    		gs = gsc.getGroceryItem(product, productFrequency);
    		gs.setProductFrequency(productFrequency);
    		totalPrice += gsc.calculateProduct(gs);
    	}
    	
    	// Computing expected Price
    	extractedItemPrice = extract.extractItemPrice(product, Constants.SALE);
    	expectedPrice = (freq%2==0 ? (extractedItemPrice * freq / 2) : (extractedItemPrice * (int)(freq/2+1)));
    	
    	expectedPrice = Utils.round(expectedPrice,2);
		totalPrice = Utils.round(totalPrice,2);
		
    	assertEquals(expectedPrice, totalPrice, 0);
    }
	
	// NEGATIVE SCENARIOS
	
	@Test
    public void calculatePcNotExisting() {
		
		String product = (rand.nextInt(20) + 4) + ""; // PRODUCT CODE NOT 1-3
		
		gs = gsc.getGroceryItem(product, 0);
		
		if (gs != null && !gs.getProductType().equals(Constants.PIECE)) {
			gs = null;
		}
		
    	assertNull(gs);
	}
	
	@Test
    public void calculateBulkNotExisting() {
		
		String product = (rand.nextInt(20) + 7) + ""; // PRODUCT CODE NOT 4-6
		
		gs = gsc.getGroceryItem(product, 0);
		
		if (gs != null && !gs.getProductType().equals(Constants.BULK)) {
			gs = null;
		}
		
    	assertNull(gs);
	}
	
	@Test
    public void calculateSaleNotExisting() {
		
		String product = (rand.nextInt(20) + 11) + ""; // PRODUCT CODE NOT 7-10
		
		gs = gsc.getGroceryItem(product, 0);
		if (gs != null && !gs.getProductType().equals(Constants.SALE)) {
			gs = null;
		}
		
    	assertNull(gs);
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
