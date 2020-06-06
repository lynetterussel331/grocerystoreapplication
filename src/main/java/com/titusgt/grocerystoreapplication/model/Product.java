package com.titusgt.grocerystoreapplication.model;

import java.math.BigDecimal;
import java.util.Comparator;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Product {

    private int code;
	private String name;
	private String type;
	private BigDecimal price;
	private double weight;
	private int frequency;

	public Product() {}

	public Product(int code, String name, String type, BigDecimal price) {
		this.code = code;
		this.name = name;
		this.type = type;
		this.price = price;
	}

	public static Comparator<Product> COMPARE_BY_PRODUCTNAME = new Comparator<Product>() {
		public int compare(Product one, Product other) {
			return one.name.compareTo(other.name);
		}
	};
}
