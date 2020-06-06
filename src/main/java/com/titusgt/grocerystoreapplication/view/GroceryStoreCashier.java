package com.titusgt.grocerystoreapplication.view;

import com.titusgt.grocerystoreapplication.GroceryStoreCalculator;
import com.titusgt.grocerystoreapplication.enums.ProductType;
import com.titusgt.grocerystoreapplication.model.Product;
import com.titusgt.grocerystoreapplication.service.GroceryStoreService;
import com.titusgt.grocerystoreapplication.utils.Validation;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;
import org.apache.commons.lang3.StringUtils;

public class GroceryStoreCashier {

    static DecimalFormat df = new DecimalFormat("#,##0.00");

    public static void displayProducts(String header, String type, int textLength) {
        System.out.println(StringUtils.center(header, textLength, "="));
        new GroceryStoreService().displayItemsByType(type, textLength);
        System.out.println("\n" + StringUtils.center("", textLength, "="));
    }

    public static void processItems(String productCode, GroceryStoreCalculator calculator,
        Scanner scanner, BigDecimal totalPrice, List<Product> productList) {
        if (Validation.isNumeric(productCode)) {
            try {
                Product product = inputItems(productCode, calculator, scanner);
                totalPrice = totalPrice.add(calculator.calculateItemPrice(product));
                productList.add(product);
                printPrice(product, totalPrice);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else if (!productCode.isEmpty()) {
            System.out.println("Invalid Product Code!");
        }
    }

    private static Product inputItems(String productCode, GroceryStoreCalculator calculator, Scanner scanner) throws Exception {

        int frequency = calculator.getFrequency(Integer.parseInt(productCode));
        Product product = calculator.getProduct(productCode, frequency);

        if (product != null) {
            System.out.println("Product Name:\t" + product.getName());

            if (product.getType().equals(ProductType.BULK.get())) {
                processBulkItems(product, scanner);
            }
        } else {
            throw new Exception("Entered Product is not existing!");
        }

        return product;
    }

    private static void printPrice(Product product, BigDecimal totalPrice) {
        System.out.println("Subtotal: \t" + df.format(product.getPrice()));
        System.out.println("Total Price: \t" + df.format(totalPrice));
    }

    private static void processBulkItems(Product product, Scanner scanner) throws Exception {
        System.out.println("Price: \t\t" + df.format(product.getPrice()) + "/kg");
        System.out.print("Weight: \t");
        String inputWeight = scanner.nextLine();

        if (Validation.isDecimal(inputWeight)) {
            double weight = Double.parseDouble(inputWeight);
            product.setWeight(weight);
            product.setPrice(product.getPrice().multiply(BigDecimal.valueOf(weight)));
        } else {
            throw new Exception("Invalid weight!");
        }
    }

    public static void printReceipt(List<Product> productList) {
        if (productList.size() > 0) {
            System.out.println(new Receipt(60).printReceipt(productList));
        } else {
            System.out.println("NO ACTUAL ORDER!");
        }
    }

}
