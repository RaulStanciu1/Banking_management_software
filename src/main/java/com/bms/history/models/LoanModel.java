package com.bms.history.models;

import com.bms.data.Loan;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.List;

public class LoanModel {
    private final SimpleDoubleProperty amountRequested;
    private final SimpleDoubleProperty paymentLeft;
    private final SimpleStringProperty loanDate;
    private final SimpleStringProperty nextPaymentDate;
    public LoanModel(Loan l){
        this.amountRequested = new SimpleDoubleProperty(l.requestAmount());
        this.paymentLeft = new SimpleDoubleProperty(l.requestLeft());
        this.loanDate = new SimpleStringProperty(l.date().toString());
        this.nextPaymentDate = new SimpleStringProperty(l.nextPayment().toString());
    }

    public double getAmountRequested() {
        return amountRequested.get();
    }

    public double getPaymentLeft() {
        return paymentLeft.get();
    }

    public String getLoanDate(){
        return loanDate.get();
    }

    public String getNextPaymentDate(){
        return nextPaymentDate.get();
    }

    public static List<LoanModel> getModelList(List<Loan> l){
        List<LoanModel> modelList = new ArrayList<>();
        for(Loan l1:l){
            modelList.add(new LoanModel(l1));
        }
        return modelList;
    }
}
