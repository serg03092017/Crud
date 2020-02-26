package com.app.logic.formattingData;

import com.app.model.DTOAllFormattedDate;

import java.util.ArrayList;
import java.util.List;

public class ViewListFormattedDate {
    public ViewListFormattedDate() {
    }

    public List<DTOAllFormattedDate> createListForView(List<com.app.DBentities.Sale> listEntityWithAllProperties) {

        List<DTOAllFormattedDate> list = new ArrayList<>();

        //получим модель с отформатированной датой

        for (com.app.DBentities.Sale i : listEntityWithAllProperties) {

            DTOAllFormattedDate dto = new DTOAllFormattedDate();

            dto.setId(i.getId());
            dto.setName(i.getName());
            dto.setLastName(i.getLastName());
            dto.setAge(i.getAge());
            dto.setPurchase_item(i.getPurchase_item());
            dto.setCount(i.getCount());
            dto.setAmount(i.getAmount());
            dto.setPurchase_date(i.getPurchase_date());

            list.add(dto);
        }
        return list;
    }

    public DTOAllFormattedDate createOneStringForView(com.app.DBentities.Sale OneValueEntityWithAllProperties) {

        DTOAllFormattedDate value;

        //получим модель с отформатированной датой

        DTOAllFormattedDate dto = new DTOAllFormattedDate();

        dto.setId(OneValueEntityWithAllProperties.getId());
        dto.setName(OneValueEntityWithAllProperties.getName());
        dto.setLastName(OneValueEntityWithAllProperties.getLastName());
        dto.setAge(OneValueEntityWithAllProperties.getAge());
        dto.setPurchase_item(OneValueEntityWithAllProperties.getPurchase_item());
        dto.setCount(OneValueEntityWithAllProperties.getCount());
        dto.setAmount(OneValueEntityWithAllProperties.getAmount());
        dto.setPurchase_date(OneValueEntityWithAllProperties.getPurchase_date());


        return dto;
    }
}
