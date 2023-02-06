package com.bms.admin;

import com.bms.data.User;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.List;

public class UserModel {
    private SimpleIntegerProperty userId;
    private SimpleStringProperty username;
    private SimpleStringProperty currency;
    private SimpleDoubleProperty balance;
    private SimpleStringProperty code;
    public UserModel(User u){
        this.userId=new SimpleIntegerProperty(u.getId());
        this.username=new SimpleStringProperty(u.getUsername());
        this.currency=new SimpleStringProperty(String.valueOf(u.getCurrency()));
        this.balance=new SimpleDoubleProperty(u.getAmount());
        this.code = new SimpleStringProperty(u.getBankingCode());
    }

    public int getUserId() {
        return userId.get();
    }
    public String getUsername(){
        return username.get();
    }

    public String getCurrency(){
        return currency.get();
    }

    public double getBalance() {
        return balance.get();
    }
    public String getCode(){
        return code.get();
    }

    public static List<UserModel> getModelList(List<User> l){
        List<UserModel> modelList = new ArrayList<>();
        for(User u:l){
            modelList.add(new UserModel(u));
        }
        return modelList;
    }
}
