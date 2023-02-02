package com.bms.data;

import java.util.List;

public class User extends UserInfo{
    private String bankingCode;
    private Currency currency;
    private double amount;
    private List<Banking> bankingHistory;
    private List<Loan> loanHistory;
    private List<Transaction> transactionHistory;
    public User(int id, String username) {
        super(id, username);
    }
}
