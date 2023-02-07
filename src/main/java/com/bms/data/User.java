package com.bms.data;

import com.bms.banking.DBBanking;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * User: A class used for storing every information related to a user of the applciation
 */
public class User extends UserInfo{
    final static String URL = "jdbc:mysql://localhost:3306/bms";
    final static String USER = "bms_admin";
    final static String PASS = "P@ssword12";
    private static final double MAX_AMOUNT = 10_000_000d;
    private final String bankingCode;
    private final Currency currency;
    private double amount;

    /**
     * Default constructor
     * @param id the id
     * @param username the username
     * @param bankingCode the banking code
     * @param currency the currency
     * @param amount the amount
     */
    public User(int id, String username,String bankingCode,int currency, double amount) {
        super(id, username);
        this.bankingCode = bankingCode;
        Currency[] currencies = new Currency[]{Currency.EURO,Currency.RON,Currency.USD};
        this.currency = currencies[currency-1];
        this.amount = amount;
    }

    /**
     * Currency getter
     * @return the currency
     */
    public Currency getCurrency(){
        return currency;
    }

    /**
     * Banking code getter
     * @return the banking code
     */
    public String getBankingCode(){
        return bankingCode;
    }

    /**
     * Amount getter
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Method used to get all the banking history of the user
     * @return a list of all banking history
     * @throws Exception exception if connection failed
     */
    public List<Banking> getBankingHistory() throws Exception{
        String SQL = "SELECT * FROM bms.banking where user = ?";
        List<Banking> bankingHistory = new ArrayList<>();
        BankingType[] types = new BankingType[]{BankingType.DEPOSIT,BankingType.WITHDRAW};
        try(Connection conn = DriverManager.getConnection(URL,USER,PASS)){
            PreparedStatement stmt = conn.prepareStatement(SQL);
            stmt.setInt(1,this.getId());
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                bankingHistory.add(
                    new Banking(rs.getInt("user"),types[rs.getInt("type")-1],
                            rs.getDouble("amount"),rs.getTimestamp("date"))
                );
            }
        }catch(Exception e){
            throw new Exception("Something went wrong");
        }
        return bankingHistory;
    }

    /**
     * Method used to get the loan history of the user
     * @return list of loans
     * @throws Exception exception if connection failed
     */
    public List<Loan> getLoanHistory() throws Exception{
        String SQL = "SELECT * FROM bms.loan where user = ?";
        List<Loan> loanHistory = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(URL,USER,PASS)){
            PreparedStatement stmt = conn.prepareStatement(SQL);
            stmt.setInt(1,this.getId());
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                loanHistory.add(new Loan(
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

    /**
     * Method used to get transaction history of the user
     * @return list of transactions
     * @throws Exception exception if connection failed
     */
    public List<Transaction> getTransactionHistory() throws Exception{
        List<Transaction> transactionHistory = new ArrayList<>();
        Currency[] currencies = new Currency[]{Currency.EURO,Currency.RON,Currency.USD};
        String SQL = "SELECT * FROM bms.transaction where sender = ?";
        try(Connection conn = DriverManager.getConnection(URL,USER,PASS)){
            PreparedStatement stmt = conn.prepareStatement(SQL);
            stmt.setInt(1,this.getId());
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                transactionHistory.add(new Transaction(
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

    /**
     * Method used to create a new transaction
     * @param amount the amount to send
     * @param receiverCode the receiver's banking code
     * @return transaction object
     * @throws Exception exception if input for the transaction is invalid
     */
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

    /**
     * Method used to create a new banking object
     * @param type the banking type(DEPOSIT or WITHDRAWAL(
     * @param amount the amount
     * @return banking object
     * @throws Exception exception if input is invalid
     */
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

    /**
     * Method used to create a new loan object
     * @param amount the amount asked
     * @return loan object
     * @throws Exception exception if input is invalid
     */
    public Loan createNewLoan(double amount) throws Exception{
        if(amount<=100) throw new Exception("Amount cannot be less or equal to 100");
        if(this.amount+amount>MAX_AMOUNT) throw new Exception("Amount is too big");
        this.amount+=amount;
        this.amount = (double) Math.round(this.amount*100)/100;
        return new Loan(this.getId(),amount,(double)Math.round(this.amount*200)/100,
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now().plusMonths(1)));
    }
}
