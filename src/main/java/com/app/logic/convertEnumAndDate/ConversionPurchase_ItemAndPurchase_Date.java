package com.app.logic.convertEnumAndDate;

import com.app.DBentities.Purchase;
import com.app.xml.entity.PurchaseItemEnum;

import javax.xml.datatype.XMLGregorianCalendar;

public class ConversionPurchase_ItemAndPurchase_Date {

    public ConversionPurchase_ItemAndPurchase_Date() {
    }

    public Purchase convertXmlPurchase_itemToDbEntity(PurchaseItemEnum purchase_item) {
        String ab = purchase_item.value();
        com.app.DBentities.Purchase b = com.app.DBentities.Purchase.fromValue(ab);
        return b;
    }

    public java.util.Date asDate(XMLGregorianCalendar purchase_date) {
        if (purchase_date == null) {
            return null;
        } else {
            return purchase_date.toGregorianCalendar().getTime();
        }
    }
}


