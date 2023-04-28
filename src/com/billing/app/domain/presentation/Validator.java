package com.billing.app.domain.presentation;

import java.util.ArrayList;

public class Validator {
    public boolean isValidConstraints(ArrayList stringArrayList) {
        if (stringArrayList.get(2).toString().isEmpty()) {
            return false;
        }
        if (stringArrayList.get(3).toString().isEmpty()) {
            return false;
        }
        if (stringArrayList.get(4).toString().isEmpty()) {
            return false;
        }
        if (stringArrayList.get(5).toString().isEmpty()) {
            return false;
        }
        if (Integer.parseInt(stringArrayList.get(6).toString()) == 0) {
            return false;
        }
        return true;
    }
}
