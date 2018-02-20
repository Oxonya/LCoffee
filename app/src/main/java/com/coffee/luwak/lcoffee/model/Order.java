package com.coffee.luwak.lcoffee.model;

public class Order {
    public String dateStr;
    public Long date;
    public Long sum;
    public boolean isCardPay;
    public String userEmail;

    public Order() {
    }

    public Order(String dateStr, Long date, Long sum, boolean isCardPay, String userEmail) {
        this.dateStr = dateStr;
        this.date = date;
        this.sum = sum;
        this.isCardPay = isCardPay;
        this.userEmail = userEmail;
    }
}
