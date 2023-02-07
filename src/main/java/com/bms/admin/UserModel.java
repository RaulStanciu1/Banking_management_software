package com.bms.admin;

import com.bms.data.User;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.List;

/**
 *  UserModel: A model class for the user table in the admin window
 */
public class UserModel {
    private SimpleIntegerProperty userId;
    private SimpleStringProperty username;
    private SimpleStringProperty currency;
    private SimpleDoubleProperty balance;
    private SimpleStringProperty code;

    /**
     * Default Constructor
     * @param u User object to convert to model
     */
    public UserModel(User u){
        this.userId=new SimpleIntegerProperty(u.getId());
        this.username=new SimpleStringProperty(u.getUsername());
        this.currency=new SimpleStringProperty(String.valueOf(u.getCurrency()));
        this.balance=new SimpleDoubleProperty(u.getAmount());
        this.code = new SimpleStringProperty(u.getBankingCode());
    }

    /**
     * User Id Getter
     * @return user id
     */
    public int getUserId() {
        return userId.get();
    }

    /**
     * Username getter
     * @return username
     */
    public String getUsername(){
        return username.get();
    }

    /**
     * Currency getter
     * @return currency
     */
    public String getCurrency(){
        return currency.get();
    }

    /**
     * Balance getter
     * @return balance
     */
    public double getBalance() {
        return balance.get();
    }

    /**
     * Code getter
     * @return code
     */
    public String getCode(){
        return code.get();
    }

    /**
     * Static method to convert a list of users to a list of user models
     * @param l list of users
     * @return list of user models
     */
    public static List<UserModel> getModelList(List<User> l){
        List<UserModel> modelList = new ArrayList<>();
        for(User u:l){
            modelList.add(new UserModel(u));
        }
        return modelList;
    }
}
