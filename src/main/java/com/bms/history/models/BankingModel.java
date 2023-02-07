package com.bms.history.models;

import com.bms.data.Banking;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * BankingMode: a model class used for displaying info on a banking table
 */
public class BankingModel {
    private final SimpleDoubleProperty amount;
    private final SimpleStringProperty date;

    /**
     * Convert a banking object to a banking model
     * @param b banking object to convert
     */
    public BankingModel(Banking b){
        this.amount = new SimpleDoubleProperty(b.amount());
        this.date = new SimpleStringProperty(b.date().toString());
    }

    /**
     * Amount getter
     * @return the amount
     */
    public double getAmount() {
        return amount.get();
    }

    /**
     * Date getter
     * @return the date
     */
    public String getDate(){
        return date.get();
    }

    /**
     * Method used to convert a list of banking objects to a list of banking models
     * @param l the list of banking
     * @return a list of banking models
     */
    public static List<BankingModel> getModelList(List<Banking> l){
        List<BankingModel> modelList = new ArrayList<>();
        for(Banking b:l){
            modelList.add(new BankingModel(b));
        }
        return modelList;
    }
}
