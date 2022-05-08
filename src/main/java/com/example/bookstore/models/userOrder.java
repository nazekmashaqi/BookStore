package com.example.bookstore.models;

import java.util.Date;
import java.util.List;

public class userOrder implements userOrderInterface {

    private String userName;
    private String createdDate;
    private double price;
    private String bookName;

    public userOrder(String userName, String createdDate, double price, String bookName) {
        this.userName = userName;
        this.createdDate = createdDate;
        this.price = price;
        this.bookName = bookName;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getCreatedDate() {
        return createdDate;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getBookName() {
        return bookName;
    }
}
