package com.billing.app.domain.service.purchase;

public class PurchaseHelp {
    public void purchase() {
        System.out.println("purchase products using following command");
        System.out.println("purchase products date, invoice, [code1, quantity1, costprice1], [code2, quantity2, costprice2]....");
        System.out.println("date - format( DD-MM-YYYY ), mandatory\n" +
                "invoice - numbers, mandatory\n" +
                "The following purchase items should be given as array of items\n" +
                "code - text, min 2 - 6 char, mandatory\n" +
                "quantity - numbers, mandatory\n" +
                "costprice - numbers, mandatory\n");
    }

    public void count() {
        System.out.println("> purchase count -d <date>\n" +
                "'date in (YYYY-MM-DD) format'\n" +
                "> purchase count\n" +
                "\n>> count : <number>");
    }
}
