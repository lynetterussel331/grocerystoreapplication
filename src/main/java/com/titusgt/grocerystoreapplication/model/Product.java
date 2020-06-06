package com.titusgt.grocerystoreapplication.model;

import java.util.Comparator;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Product {

    private int code;
	private String name;
	private String type;
	private double price;
	private double weight;
	private String saleType;
	private int frequency;

	public Product() {}

	public Product(int code, String name, String type, double price) {
		this.code = code;
		this.name = name;
		this.type = type;
		this.price = price;
	}

	public Product(int code, String name, String type, double price, String saleType) {
		this.code = code;
		this.name = name;
		this.type = type;
		this.price = price;
		this.saleType = saleType;
	}

	public static Comparator<Product> COMPARE_BY_PRODUCTNAME = new Comparator<Product>() {
		public int compare(Product one, Product other) {
			return one.name.compareTo(other.name);
		}
	};
}
