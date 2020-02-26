package com.app.DBentities;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Component
@Entity
@Table(name = "SALE")
public class Sale implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_ID")
    private long id;

    @Column(name = "sale_name")
    private String name;

    @Column(name = "sale_last_name")
    private String lastName;

    @Column(name = "sale_age")
    private int age;

    @Enumerated(EnumType.STRING)
    @Column(name = "sale_purchase_item")
    private Purchase purchase_item;

    @Column(name = "sale_count")
    private int count;

    @Column(name = "sale_amount")
    private float amount;

    @Temporal(TemporalType.DATE)
    @Column(name = "sale_purchase_date")
    private Date purchase_date;

    public Sale() {
    }

    public Sale(String name, String lastName, int age, Purchase purchase_item, int count, float amount, Date purchase_date) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.purchase_item = purchase_item;
        this.count = count;
        this.amount = amount;
        this.purchase_date = purchase_date;
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

    public Date getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(Date purchase_date) {
        this.purchase_date = purchase_date;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age='" + age + '\'' +
                ", purchase_item='" + purchase_item + '\'' +
                ", count=" + count +
                ", amount=" + amount +
                ", purchase_date=" + purchase_date +
                '}';
    }
}