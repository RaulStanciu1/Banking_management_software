package com.bms.history;

import com.bms.data.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DBHistory {
    final static String URL = "jdbc:mysql://localhost:3306/bms";
    final static String USER = "bms_admin";
    final static String PASS = "P@ssword";
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
