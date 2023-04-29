package com.billing.app.domain.presentation;

import java.util.ArrayList;

public class Validator {
    public boolean isValidConstraints(ArrayList stringArrayList) {
        for (Object object : stringArrayList) {
            if (isNull(object)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNull(Object value) {
        return value == null;
    }

}
