package com.app.model;

import com.app.DBentities.Purchase;

public class DTOSalePurchaseItem {

    Purchase purchase_item;

    public DTOSalePurchaseItem() {
    }

    public DTOSalePurchaseItem(Purchase purchase_item) {
        this.purchase_item = purchase_item;
    }

    public void setPurchase_item(Purchase purchase_item) {
        this.purchase_item = purchase_item;
    }

    public Purchase getPurchase_item() {
        return purchase_item;
    }

    @Override
    public String toString() {
        return "DTOSalePurchaseItem{" +
                "purchase_item='" + purchase_item + '\'' +
                '}';
    }
}
