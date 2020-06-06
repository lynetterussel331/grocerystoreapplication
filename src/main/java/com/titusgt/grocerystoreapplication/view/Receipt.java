package com.titusgt.grocerystoreapplication.view;

import com.titusgt.grocerystoreapplication.utils.ProductType;
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

	public Receipt(int ROW_LENGTH) {
		this.ROW_LENGTH = ROW_LENGTH;
	}

	final DateTimeFormatter dtFormat = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
	final DecimalFormat df = new DecimalFormat("#,##0.00");
	StringBuilder sb = new StringBuilder();

	public String printReceipt(List<Product> productList) {

		printHeader();
		BigDecimal totalPrice = new BigDecimal(0);
		Collections.sort(productList, Product.COMPARE_BY_PRODUCTNAME);

		for (Product product : productList) {
			sb.append(StringUtils.rightPad(product.getName(), ROW_LENGTH / 2) +
				StringUtils.rightPad(df.format(product.getPrice()), ROW_LENGTH / 2) + "\n");

			if (product.getType().equals(ProductType.BULK.get())) {
				BigDecimal pricePerItem = product.getPrice().divide(new BigDecimal(product.getWeight()));
				sb.append("- 1 @ " + df.format(pricePerItem) + "\tX " + product.getWeight() + "KG\n");
			} else if (product.getType().equals(ProductType.SALE.get())
				&& product.getPrice().compareTo(new BigDecimal(0)) == 0) {
				sb.append("- " + product.getSaleType() + "\n");
			}

			totalPrice = totalPrice.add(product.getPrice());
		}
		printFooter(totalPrice);

		return sb.toString();
	}

	private void printHeader() {
		sb.append(StringUtils.center(" SM Supermarket ", ROW_LENGTH, "=") + "\n");
		sb.append(StringUtils.center(" " + dtFormat.format(LocalDateTime.now()) + " ", ROW_LENGTH, "=") + "\n\n");
		sb.append(StringUtils.leftPad("", ROW_LENGTH / 2, " ") + "PHP\n");
	}

	private void printFooter(BigDecimal totalPrice) {
		sb.append("\n" + StringUtils.center(" NOTHING FOLLOWS ", ROW_LENGTH, "-"));
		sb.append("\n" + StringUtils.center(" Total Price: " + df.format(totalPrice) + " ", ROW_LENGTH, "-"));
		sb.append("\n" + StringUtils.leftPad("", ROW_LENGTH, "="));
	}

}
