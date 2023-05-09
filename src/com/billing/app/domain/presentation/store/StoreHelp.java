package com.billing.app.domain.presentation.store;

public class StoreHelp {
    public void createStore() {
        System.out.println("Create store using the following template.");
        System.out.println("gst number, name, phonenumber, address\n");
        System.out.println("name  - text, mandatory with 3 to 30 chars\n" +
                "phone - number, mandatory, ten digits, digit start with 9/8/7/6\n" +
                "address - text, mandatory\n" +
                "gst number - text, 15 digit, mandatory");
        System.out.println(">> store create name, phone number, address, gst number  (or)\n" +
                ">> store create :enter\n" +
                ">> name, phone number, address, gst number");
    }

    public void editStore() {
        System.out.println(" Edit store using the following template.");
        System.out.println("name, phone number, address, gst number");
        System.out.println("name  - text, mandatory with 3 to 30 chars\n" +
                "phone - number, mandatory, ten digits, digit start with 9/8/7/6\n" +
                "address - text, mandatory\n" +
                "gst number - text, 15 digit, mandatory");
        System.out.println(">> store edit name, phone number, address, gst number   (or)\n" +
                ">> store edit :enter\n" +
                ">> name, phone number, address, gst number");
    }

    public void deleteStore() {
        System.out.println(">> store delete");
        System.out.println("Provide admin password to delete the store.");
    }

    public void viewStore() {
        System.out.println(">> store view");
        System.out.println("Displays the details of the store.");
    }
}
