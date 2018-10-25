package com.titusgt.grocerystoreapplication.model;

import java.util.Comparator;

import com.titusgt.grocerystoreapplication.model.GroceryStore;

public class GroceryStore {

	private int productCode;
	private String productName;
	private String productType;
	private double price;
	private double weight;
	private String saleType;
	private int productFrequency;
	
	public int getProductCode() {
		return productCode;
	}
	public void setProductCode(int productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public String getSaleType() {
		return saleType;
	}
	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}
	public int getProductFrequency() {
		return productFrequency;
	}
	public void setProductFrequency(int productFrequency) {
		this.productFrequency = productFrequency;
	}
	
	@Override
	public String toString() {
		
		return "[ " + this.productCode + ", " + this.productName 
			 + ", " + this.productType  + ", " + this.price
					+ (this.productType.equals("BULK") ? (", " + this.weight) : "")
					+ (this.productType.equals("SALE") ? ( ", " + this.saleType) : "" ) + " ]";
		
	}
	
	public static Comparator<GroceryStore> COMPARE_BY_PRODUCTNAME = new Comparator<GroceryStore>() {
        public int compare(GroceryStore one, GroceryStore other) {
            return one.productName.compareTo(other.productName);
        }
    };
	
}
