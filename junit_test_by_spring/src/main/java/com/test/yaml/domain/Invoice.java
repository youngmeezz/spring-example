package com.test.yaml.domain;

import java.util.List;

import com.google.gson.Gson;

public class Invoice {
    public Integer invoice; // invoice
    public String date; // date
    public Person2 billTo;// bill-to
    public Person2 shipTo;// ship-to
    public List<Product> product;
    public Float tax;
    public Float total;
    public String comments;
    
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
    
}
