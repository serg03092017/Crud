package com.app.model;

import com.app.DBentities.Purchase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DTOAllFormattedDate {


    private long id;

    private String name;

    private String lastName;

    private int age;

    private Purchase purchase_item;

    private int count;

    private float amount;

    private String purchase_date;

    public DTOAllFormattedDate() {
    }

    public DTOAllFormattedDate(long id, String name, String lastName, int age, Purchase purchase_item, int count, float amount, Date purchase_date) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.purchase_item = purchase_item;
        this.count = count;
        this.amount = amount;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        this.purchase_date = sdf.format(purchase_date);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Purchase getPurchase_item() {
        return purchase_item;
    }

    public void setPurchase_item(Purchase purchase_item) {
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

    public String getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(Date purchase_date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        this.purchase_date = sdf.format(purchase_date);
    }
}
