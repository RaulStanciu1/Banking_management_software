package com.bms.history.models;

import com.bms.data.Banking;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.List;

public class BankingModel {
    private final SimpleDoubleProperty amount;
    private final SimpleStringProperty date;
    public BankingModel(Banking b){
        this.amount = new SimpleDoubleProperty(b.amount());
        this.date = new SimpleStringProperty(b.date().toString());
    }

    public double getAmount() {
        return amount.get();
    }

    public String getDate(){
        return date.get();
    }

    public static List<BankingModel> getModelList(List<Banking> l){
        List<BankingModel> modelList = new ArrayList<>();
        for(Banking b:l){
            modelList.add(new BankingModel(b));
        }
        return modelList;
    }
}
