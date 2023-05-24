package com.billing.app.domain.presentation.unit;

public class UnitHelp {
    public void createUnit() {
        System.out.println("\nCreate unit using the following template:");
        System.out.println(">> unit create name, code, description, isdividable  (or)\n" +
                ">> unit create :(enter) \n" +
                "name, code, description, isdividable\n");
        System.out.println("Ensure the attributes are of provided template.\n" +
                "name - text, mandatory with 3 to 30 chars\n" +
                "code - text, maximum 4 char, mandatory\n" +
                "description - text\n" +
                "isdividable - boolean, mandatory");

    }

    public void editUnit() {
        System.out.println("Edit unit using the following template:");
        System.out.println("id: <id - 6>, name: <name-edited>, code: <code>,  description: <description>, isdividable: <isdividable>\n");
        System.out.println("You can not give empty or null values to the mandatory attributes.\n" +
                "name - text, mandatory with 3 to 30 chars\t\n" +
                "code - text, maximum 4 char, mandatory\n" +
                "description - text\n" +
                "isdividable - boolean, mandatory\n");
        System.out.println(">> unit edit id: <id - 6>, name: <name-edited>, code: <code>,  description: <description>, isdividable: <isdividable>   (or)\n" +
                "unit edit id: enter:\n" +
                "<id - 6>, name: <name-edited>, code: <code>,  description: <description>, isdividable: <isdividable>\n");
    }

    public void deleteUnit() {
        System.out.println("Delete unit using the following template");
        System.out.println("Provide Unit <id> for deletion. ");
        System.out.println(">> unit delete <id>");
    }

    public void listUnit() {
        System.out.println("List unit with the following options\n" +
                ">> unit list - will list all the units");
    }
}
