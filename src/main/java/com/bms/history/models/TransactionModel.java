package com.bms.history.models;

import com.bms.data.Transaction;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.List;

public class TransactionModel {
    private final SimpleIntegerProperty receiver;
    private final SimpleDoubleProperty amount;
    private final SimpleStringProperty receiverCurrency;
    private final SimpleStringProperty date;

    public TransactionModel(Transaction t){
        this.receiver = new SimpleIntegerProperty(t.receiverId());
        this.amount = new SimpleDoubleProperty(t.amount());
        this.receiverCurrency = new SimpleStringProperty(t.receiverCurr().getSymbol());
        this.date = new SimpleStringProperty(t.date().toString());
    }

    public double getAmount() {
        return amount.get();
    }
    public int getReceiver(){
        return receiver.get();
    }

    public String getReceiverCurrency(){
        return receiverCurrency.get();
    }
    public String getDate(){
        return date.get();
    }

    public static List<TransactionModel> getModelList(List<Transaction> l){
        List<TransactionModel> modelList = new ArrayList<>();
        for(Transaction t:l){
            modelList.add(new TransactionModel(t));
        }
        return modelList;
    }
}
