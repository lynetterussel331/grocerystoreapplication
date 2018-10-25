package com.titusgt.grocerystoreapplication;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.titusgt.grocerystoreapplication.model.GroceryStore;
import com.titusgt.grocerystoreapplication.utils.Constants;
import com.titusgt.grocerystoreapplication.utils.Utils;
import com.titusgt.grocerystoreapplication.view.Receipt;

public class CalculateTestMultiple {

	private GroceryStore gs;
	private GroceryStoreCalculator gsc;
	private ProductPrice extract;
	private Random rand;
	private List<GroceryStore> groupOfProducts;
	
	@Before
	public void setUp() {
		gsc = new GroceryStoreCalculator();
		extract = new ProductPrice();
		rand = new Random();
		groupOfProducts = new ArrayList<GroceryStore>();
	}
	
	// POSITIVE SCENARIOS
	@Test
	public void calculateGroupOfProducts() {
		
		double totalPrice = 0;
		double expectedPrice = 0;
		
		int freqProduct1 = rand.nextInt(10) + 1; // PRODUCT FREQUENCY (PC)
		int freqProduct2 = rand.nextInt(10) + 1; // PRODUCT FREQUENCY (BULK)
		int freqProduct3 = rand.nextInt(10) + 1; // PRODUCT FREQUENCY (SALE)
		
		// BULK PRODUCTS
		for (int i = 1; i <= freqProduct2; i++) {
			
			int weight = rand.nextInt(10) + 1;
			String productCode2= (rand.nextInt(3) + 4) + ""; // PRODUCT FREQUENCY (BULK)
			
			gs = gsc.getGroceryItem(productCode2, 0);
			gs.setWeight(weight);
			gs.setPrice(gs.getPrice() * weight);
			totalPrice += gsc.calculateProduct(gs);
			expectedPrice += extract.extractItemPrice(productCode2, Constants.BULK) * weight;
			groupOfProducts.add(gs);
		}
		
		// SALE
		for (int i = 1; i <= freqProduct3; i++) {

			String productCode3 = (rand.nextInt(4) + 7) + ""; // PRODUCT FREQUENCY (SALE)
			int currentProductFreq = rand.nextInt(10) + 1; // CURRENT FREQUENCY

	    	double extractedItemPrice = extract.extractItemPrice(productCode3, Constants.SALE);

			for (int j = 1; j <= currentProductFreq; j++) {
	    		int productFrequency = gsc.getProductFrequency(Integer.parseInt(productCode3));
				gs = gsc.getGroceryItem(productCode3, productFrequency);
	    		gs.setProductFrequency(productFrequency);
				totalPrice += gsc.calculateProduct(gs);
				groupOfProducts.add(gs);
			}
			
			// Computing expected Price
	    	expectedPrice += (currentProductFreq%2==0 ? (extractedItemPrice * currentProductFreq / 2) : (extractedItemPrice * (int)(currentProductFreq/2+1)));
			
		}
		
		// BY PIECE
		for (int i = 1; i <= freqProduct1; i++) {
			
			String productCode1 = (rand.nextInt(3) + 1) + ""; // PRODUCT FREQUENCY (PC)
			
			gs = gsc.getGroceryItem(productCode1, 0);
			totalPrice += gsc.calculateProduct(gs);
			expectedPrice += extract.extractItemPrice(productCode1, Constants.PIECE);
			groupOfProducts.add(gs);
		}
		
		expectedPrice = Utils.round(expectedPrice,2);
		totalPrice = Utils.round(totalPrice,2);
		
		assertEquals(expectedPrice, totalPrice, 0);
		
		System.out.println(new Receipt().printReceipt(groupOfProducts));
	}
	
}
