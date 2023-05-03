package com.billing.app.domain.presentation;

import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.exceptions.CustomException;
import com.billing.app.domain.exceptions.ProductException;
import com.billing.app.domain.service.UnitService;
import com.billing.app.domain.service.UnitServiceInterface;

import java.sql.SQLException;
import java.util.ArrayList;

public class UnitParser {
    UnitServiceInterface unitServiceInterface;
    public void create(ArrayList<String> arrayList) throws SQLException, CustomException, ClassNotFoundException, ProductException {
        ArrayList<String> values = new ArrayList<>(arrayList.subList(2, arrayList.size()));
        Unit unit = new Unit();
        unit.setName(values.get(0));
        unit.setCode(values.get(1));
        unit.setDescription(values.get(2));
        unit.setDividable(Boolean.parseBoolean(values.get(3)));
        unitServiceInterface = new UnitService();
        unitServiceInterface.create(unit);
    }


}
