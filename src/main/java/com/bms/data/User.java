package com.bms.data;

import com.bms.banking.DBBanking;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class User extends UserInfo{
    final static String URL = "jdbc:mysql://localhost:3306/bms";
    final static String USER = "bms_admin";
    final static String PASS = "P@ssword";
    private static final double MAX_AMOUNT = 10_000_000d;
    private final String bankingCode;
    private final Currency currency;
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
    }
    public Currency getCurrency(){
        return currency;
    }
    public String getBankingCode(){
        return bankingCode;
    }

    public double getAmount() {
        return amount;
    }

    public List<Banking> getBankingHistory() throws Exception{
        String SQL = "SELECT * FROM bms.banking where user = ?";
        this.bankingHistory = new ArrayList<>();
        BankingType[] types = new BankingType[]{BankingType.DEPOSIT,BankingType.WITHDRAW};
        try(Connection conn = DriverManager.getConnection(URL,USER,PASS)){
            PreparedStatement stmt = conn.prepareStatement(SQL);
            stmt.setInt(1,this.getId());
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                this.bankingHistory.add(
                    new Banking(rs.getInt("user"),types[rs.getInt("type")-1],
                            rs.getDouble("amount"),rs.getTimestamp("date"))
                );
            }
        }catch(Exception e){
            throw new Exception("Something went wrong");
        }
        return bankingHistory;
    }

    public List<Loan> getLoanHistory() throws Exception{
        String SQL = "SELECT * FROM bms.loan where user = ?";
        this.loanHistory = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(URL,USER,PASS)){
            PreparedStatement stmt = conn.prepareStatement(SQL);
            stmt.setInt(1,this.getId());
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                this.loanHistory.add(new Loan(
                        rs.getInt("id"),rs.getDouble("request_amount"),
                        rs.getDouble("request_left"),rs.getTimestamp("date"),
                        rs.getTimestamp("next_payment")
                ));
            }
        }catch(Exception e){
            throw new Exception("Something went wrong");
        }
        return loanHistory;
    }

    public List<Transaction> getTransactionHistory() throws Exception{
        this.transactionHistory = new ArrayList<>();
        Currency[] currencies = new Currency[]{Currency.EURO,Currency.RON,Currency.USD};
        String SQL = "SELECT * FROM bms.transaction where sender = ?";
        try(Connection conn = DriverManager.getConnection(URL,USER,PASS)){
            PreparedStatement stmt = conn.prepareStatement(SQL);
            stmt.setInt(1,this.getId());
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                this.transactionHistory.add(new Transaction(
                            rs.getInt("sender"),rs.getInt("receiver"),
                            rs.getTimestamp("date"),currencies[rs.getInt("receiver_currency")-1],
                            rs.getDouble("amount")
                        ));
            }
        }catch(Exception e){
            throw new Exception("Something went wrong");
        }
        return transactionHistory;
    }

    public Transaction createNewTransaction(double amount,String receiverCode) throws Exception{
        if(amount>this.amount) throw new Exception("Amount is invalid");
        if(amount<=0) throw new Exception("Amount cannot be less or equal to 0");
        if(receiverCode.equals(this.bankingCode)) throw new Exception("Invalid banking code");
        int receiverId = DBBanking.bankingCodeExists(receiverCode);
        Currency receiverCurr = DBBanking.getUserCurrency(receiverId);
        this.amount-=amount;
        this.amount = (double) Math.round(this.amount*100)/100;
        return new Transaction(this.getId(),receiverId, Timestamp.valueOf(LocalDateTime.now()),receiverCurr,amount);
    }

    public Banking createNewBanking(BankingType type,double amount) throws Exception{
        if(amount <= 0) throw new Exception("Amount is invalid");
        if(type == BankingType.DEPOSIT){
            if(this.amount+amount>MAX_AMOUNT) throw new Exception("Amount is too big");
            this.amount+=amount;
            this.amount = (double) Math.round(this.amount*100)/100;
            return new Banking(this.getId(),BankingType.DEPOSIT,amount,Timestamp.valueOf(LocalDateTime.now()));
        }else{
            if(amount>this.amount) throw new Exception("Amount is too big");
            this.amount-=amount;
            this.amount = (double) Math.round(this.amount*100)/100;
            return new Banking(this.getId(),BankingType.WITHDRAW,amount,Timestamp.valueOf(LocalDateTime.now()));
        }
    }
}
