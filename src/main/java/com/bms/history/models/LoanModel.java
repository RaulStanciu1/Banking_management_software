package com.bms.history.models;

import com.bms.data.Loan;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * LoanModel: a model class used for displaying info on the loan table
 */
public class LoanModel {
    private final SimpleDoubleProperty amountRequested;
    private final SimpleDoubleProperty paymentLeft;
    private final SimpleStringProperty loanDate;
    private final SimpleStringProperty nextPaymentDate;

    /**
     * Converting a loan object to a loan model
     * @param l the loan to convert
     */
    public LoanModel(Loan l){
        this.amountRequested = new SimpleDoubleProperty(l.requestAmount());
        this.paymentLeft = new SimpleDoubleProperty(l.requestLeft());
        this.loanDate = new SimpleStringProperty(l.date().toString());
        this.nextPaymentDate = new SimpleStringProperty(l.nextPayment().toString());
    }

    /**
     * Amount requested getter
     * @return the amount requested
     */
    public double getAmountRequested() {
        return amountRequested.get();
    }

    /**
     * Payment left getter
     * @return the payment left
     */
    public double getPaymentLeft() {
        return paymentLeft.get();
    }

    /**
     * Loan date getter
     * @return the loan date
     */
    public String getLoanDate(){
        return loanDate.get();
    }

    /**
     * Next payment date getter
     * @return the next payment date
     */
    public String getNextPaymentDate(){
        return nextPaymentDate.get();
    }

    /**
     * Method used to convert a list of loan objects to a list of loan models
     * @param l the list of loan objects
     * @return a list of loan models
     */
    public static List<LoanModel> getModelList(List<Loan> l){
        List<LoanModel> modelList = new ArrayList<>();
        for(Loan l1:l){
            modelList.add(new LoanModel(l1));
        }
        return modelList;
    }
}
