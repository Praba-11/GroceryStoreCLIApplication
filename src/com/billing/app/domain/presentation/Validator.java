package com.billing.app.domain.presentation;

import com.billing.app.domain.entity.Product;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class Validator {
    public static boolean isNull(String value) {
        return value == null;
    }

    public boolean createByValidation(ArrayList<String> values) {
        for (String value : values) {
            if (isNull(value)) {
                return false;
            }
        }
        return true;
    }


}
