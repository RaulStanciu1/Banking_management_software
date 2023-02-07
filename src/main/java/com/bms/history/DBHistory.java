package com.bms.history;

import com.bms.data.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * DBHistory: A class used for database queries related to the history windows
 */
public class DBHistory {
    final static String URL = "jdbc:mysql://localhost:3306/bms";
    final static String USER = "bms_admin";
    final static String PASS = "P@ssword12";

    /**
     * Method used to get a banking given a type and a user
     * @param userId the user id
     * @param type the banking type(deposit or withdraw)
     * @return a list of all banking made by that user
     * @throws Exception exception if connection failed
     */
    public static List<Banking> getBankingList(int userId, BankingType type) throws Exception{
        List<Banking> list = new ArrayList<>();
        String SQL="SELECT * FROM bms.banking WHERE user=? AND type=?";
        try(Connection conn = DriverManager.getConnection(URL,USER,PASS)){
            PreparedStatement stmt = conn.prepareStatement(SQL);
            stmt.setInt(1,userId);
            stmt.setString(2,type.toString());
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                list.add(new Banking(userId,type,rs.getDouble("amount"),rs.getTimestamp("date")));
            }
        }catch(Exception e){
            throw new Exception("Something went wrong");
        }
        return list;
    }

    /**
     * Method used to get loans asked by a user
     * @param userId the user id
     * @return list of loans
     * @throws Exception exception if connection failed
     */
    public static List<Loan> getLoanList(int userId) throws Exception{
        List<Loan> list = new ArrayList<>();
        String SQL="SELECT * FROM bms.loan WHERE user=?";
        try(Connection conn = DriverManager.getConnection(URL,USER,PASS)){
            PreparedStatement stmt = conn.prepareStatement(SQL);
            stmt.setInt(1,userId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                list.add(new Loan(userId,rs.getDouble("request_amount"),
                                rs.getDouble("request_left"),rs.getTimestamp("date"),
                                rs.getTimestamp("next_payment")));
            }
        }catch(Exception e){
            System.out.println(e);
            throw new Exception("Something went wrong");
        }
        return list;
    }

    /**
     * Method used to get transactions made by a user
     * @param userId the user id
     * @return list of transactions
     * @throws Exception exception if the connection failed
     */
    public static List<Transaction> getTransactionList(int userId) throws Exception{
        List<Transaction> list = new ArrayList<>();
        String SQL="SELECT * FROM bms.transaction WHERE sender=?";
        Currency[] currencies = new Currency[]{Currency.EURO,Currency.RON, Currency.USD};
        try(Connection conn = DriverManager.getConnection(URL,USER,PASS)){
            PreparedStatement stmt = conn.prepareStatement(SQL);
            stmt.setInt(1,userId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                list.add(new Transaction(userId,rs.getInt("receiver"),
                        rs.getTimestamp("date"),
                        currencies[rs.getInt("receiver_currency")],rs.getDouble("amount")));
            }
        }catch(Exception e){
            throw new Exception("Something went wrong");
        }
        return list;
    }
}
