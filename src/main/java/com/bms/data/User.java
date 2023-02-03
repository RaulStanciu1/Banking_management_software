package com.bms.data;

import java.util.ArrayList;
import java.util.List;

public class User extends UserInfo{
    private String bankingCode;
    private Currency currency;
    private double amount;
    private List<Banking> bankingHistory;
    private List<Loan> loanHistory;
    private List<Transaction> transactionHistory;
    public User(int id, String username,String bankingCode,int currency, double amount) {
        super(id, username);
        this.bankingCode = bankingCode;
        Currency[] currencies = new Currency[]{Currency.EURO,Currency.RON,Currency.USD};
        this.currency = currencies[currency-1];
        this.amount = amount;
        this.bankingHistory = new ArrayList<>();
        this.loanHistory = new ArrayList<>();
        this.transactionHistory = new ArrayList<>();
    }
    public String toString(){
        return this.getId()+" "+this.getUsername()+" "+bankingCode+" "+amount+" "+currency;
    }
}
