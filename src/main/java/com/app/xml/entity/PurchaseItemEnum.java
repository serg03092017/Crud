package com.app.xml.entity;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "purchase_itemEnum")
@XmlEnum
public enum PurchaseItemEnum {
    TV, SMARTPHONE, JUICER, HEADPHONES, KEYBOARD;


    public String value() {
        return name();
    }



    public static PurchaseItemEnum fromValue(String v) {
        return valueOf(v);
    }


}