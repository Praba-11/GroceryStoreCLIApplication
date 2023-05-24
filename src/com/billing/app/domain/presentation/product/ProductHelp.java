package com.billing.app.domain.presentation.product;

public class ProductHelp {
    public void createProduct() {
        System.out.println("\nCreate product using following template:");
        System.out.println(">> product create code, name, unitcode, type, price, stock   (or)");
        System.out.println(">> product create :(enter)\n" +
                "code, name, unitcode, type, price, stock\n");
        System.out.println("Ensure the attributes are of provided template.\n" +
                "code - text, min - 2 - 6, mandatory\n" +
                "name - text, min 3 - 30 char, mandatory\n" +
                "unitcode - text, kg/l/piece/combo, mandatory\n" +
                "type - text, between enumerated values, mandatory \n" +
                "price - number, mandatory\n" +
                "stock - number\n");

    }

    public void editProduct() {
        System.out.println("\nEdit product using following template. Copy the product data from the list, edit the attribute values.");
        System.out.println(">> product edit id: <id>, code: <code>, name: <name-edited>, unitcode: <unitcode>,  type: <type>, price: <price>\n");
        System.out.println(">> product edit :enter\n" +
                ">> id: <id - 6>, name: <name-edited>, unitcode: <unitcode>,  type: <type>, price: <price>\n");
        System.out.println("Ensure the attributes are of provided template.\n" +
                "id - numeric, mandatory\n" +
                "code - text, min - 2 - 6, mandatory\n" +
                "name - text, min 3 - 30 char, mandatory\n" +
                "unitcode - text, kg/l/piece/combo, mandatory\n" +
                "type - text, between enumerated values, mandatory \n" +
                "price - numeric, mandatory\n");
    }

    public void deleteProduct() {
        System.out.println("delete product using the following template\n" +
                "id - numeric, existing\n" +
                "code - text, min - 2 - 6, mandatory\n");
        System.out.println(">> product delete -c <code>\n" +
                ">> product delete -i <id>\n");
    }

    public void listProduct() {
        System.out.println("List product with the following options.");
        System.out.println("product list - will list all the products default to maximum up to 20 products");
        System.out.println("product list -p 10 - pageable list shows 10 products as default");
        System.out.println("product list -p 10 3 - pagable list shows 10 products in 3rd page, ie., product from 21 to 30");
        System.out.println("product list -s searchtext - search the product with the given search text in all the searchable attributes");
        System.out.println("product list -s <attr>: searchtext - search the product with the given search text in all the given attribute");
        System.out.println("product list -s <attr>: searchtext -p 10 6 - pagable list shows 10 products in 6th page with the given search text in the given attribute\n");
    }
}
