package com.app.xml.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sale", propOrder = {
        "name",
        "lastName",
        "age",
        "purchase_item",
        "count",
        "amount",
        "purchase_date"
})
@XmlRootElement(name = "sale")
public class Sale {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String lastName;
    @XmlElement(required = true)
    private int age;
    @XmlElement(name = "purchase_item", required = true)
    @XmlSchemaType(name = "string")
    private PurchaseItemEnum purchase_item;
    @XmlElement(required = true)
    private int count;
    @XmlElement(required = true)
    private float amount;
    @XmlSchemaType(name = "date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private XMLGregorianCalendar purchase_date;

    public Sale() {

    }

    public Sale(String name, String lastName, int age, PurchaseItemEnum purchase_item, int count, float amount, XMLGregorianCalendar purchase_date) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.purchase_item = purchase_item;
        this.count = count;
        this.amount = amount;
        this.purchase_date = purchase_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public PurchaseItemEnum getPurchase_item() {
        return purchase_item;
    }

    public void setPurchase_item(PurchaseItemEnum purchase_item) {
        this.purchase_item = purchase_item;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public XMLGregorianCalendar getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(XMLGregorianCalendar purchase_date) {
        this.purchase_date = purchase_date;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", purchase_item=" + purchase_item +
                ", count=" + count +
                ", amount=" + amount +
                ", purchase_date=" + purchase_date +
                '}';
    }
}