package com.titusgt.grocerystoreapplication.view;

import com.titusgt.grocerystoreapplication.enums.ProductType;
import com.titusgt.grocerystoreapplication.enums.SaleType;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collections;
import java.util.List;

import com.titusgt.grocerystoreapplication.model.Product;
import org.apache.commons.lang3.StringUtils;

public class Receipt {

	final int ROW_LENGTH;
	final DateTimeFormatter dtFormat = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
	final DecimalFormat df = new DecimalFormat("#,##0.00");
	StringBuilder sb = new StringBuilder();

	public Receipt(int ROW_LENGTH) {
		this.ROW_LENGTH = ROW_LENGTH;
	}

	public String printReceipt(List<Product> productList) {

		printHeader();
		BigDecimal totalPrice = new BigDecimal(0);
		Collections.sort(productList, Product.COMPARE_BY_PRODUCTNAME);

		for (Product product : productList) {

			sb.append("\n" + StringUtils.rightPad(product.getName(), ROW_LENGTH / 2) +
				StringUtils.rightPad(df.format(product.getPrice()), ROW_LENGTH / 2));

			if (product.getType().equals(ProductType.BULK.get())) {
				String pricePerItem = df.format(product.getPrice().divide(new BigDecimal(product.getWeight())));
				sb.append("\n" + "- 1 @ " + pricePerItem + "\tX " + product.getWeight() + "KG");
			} else if (product.getType().equals(ProductType.SALE.get())
				&& product.getPrice().compareTo(new BigDecimal(0)) == 0) {
				sb.append("\n" + "- " + SaleType.BUY1_GET1.get());
			}

			totalPrice = totalPrice.add(product.getPrice());
		}
		printFooter(totalPrice);

		return sb.toString();
	}

	private void printHeader() {
		sb.append("\n" + StringUtils.center(" SM Supermarket ", ROW_LENGTH, "="));
		sb.append("\n" + StringUtils.center(" " + dtFormat.format(LocalDateTime.now()) + " ", ROW_LENGTH, "="));
		sb.append("\n\n" + StringUtils.leftPad("", ROW_LENGTH / 2, " ") + "PHP");
	}

	private void printFooter(BigDecimal totalPrice) {
		sb.append("\n" + StringUtils.center(" NOTHING FOLLOWS ", ROW_LENGTH, "-"));
		sb.append("\n" + StringUtils.center(" Total Price: " + df.format(totalPrice) + " ", ROW_LENGTH, "-"));
		sb.append("\n" + StringUtils.leftPad("", ROW_LENGTH, "="));
	}

}
