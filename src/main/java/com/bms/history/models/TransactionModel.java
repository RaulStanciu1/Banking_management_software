package com.bms.history.models;

import com.bms.data.Transaction;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * TransactionModel: a model class used for displaying info on the transaction table
 */
public class TransactionModel {
    private final SimpleIntegerProperty receiver;
    private final SimpleDoubleProperty amount;
    private final SimpleStringProperty receiverCurrency;
    private final SimpleStringProperty date;

    /**
     * Convert a transaction object to a transaction model
     * @param t the transaction
     */
    public TransactionModel(Transaction t){
        this.receiver = new SimpleIntegerProperty(t.receiverId());
        this.amount = new SimpleDoubleProperty(t.amount());
        this.receiverCurrency = new SimpleStringProperty(t.receiverCurr().getSymbol());
        this.date = new SimpleStringProperty(t.date().toString());
    }

    /**
     * Amount getter
     * @return the amount
     */
    public double getAmount() {
        return amount.get();
    }

    /**
     * Receiver getter
     * @return the receiver
     */
    public int getReceiver(){
        return receiver.get();
    }

    /**
     * Receiver currency getter
     * @return the receiver currency
     */
    public String getReceiverCurrency(){
        return receiverCurrency.get();
    }

    /**
     * Date getter
     * @return the date
     */
    public String getDate(){
        return date.get();
    }

    /**
     * Method used to convert a list of transaction objects to a list of transaction models
     * @param l list of transactions
     * @return a list of transaction models
     */
    public static List<TransactionModel> getModelList(List<Transaction> l){
        List<TransactionModel> modelList = new ArrayList<>();
        for(Transaction t:l){
            modelList.add(new TransactionModel(t));
        }
        return modelList;
    }
}
