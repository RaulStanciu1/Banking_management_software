package com.bms.banking;

import com.bms.data.Banking;
import com.bms.data.Currency;
import com.bms.data.Transaction;
import com.bms.data.User;

import java.sql.*;

public class DBBanking {
    final static String URL = "jdbc:mysql://localhost:3306/bms";
    final static String USER = "bms_admin";
    final static String PASS = "P@ssword";
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
    public static int bankingCodeExists(String code) throws Exception{
        int receiverId=-1;
        String SQL = "SELECT id FROM bms.user WHERE banking_code=?";
        try(Connection conn = DBBanking.connect()){
            PreparedStatement stmt = conn.prepareStatement(SQL);
            stmt.setString(1,code);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                receiverId=rs.getInt(1);
            }
        }catch(Exception e){
            throw new Exception("Something went wrong");
        }
        if(receiverId==-1) throw new Exception("Banking code doesn't exist");
        return receiverId;
    }

    public static Currency getUserCurrency(int id) throws Exception{
        Currency curr = null;
        Currency[] currencies = new Currency[]{Currency.EURO,Currency.RON,Currency.USD};
        String SQL = "SELECT currency FROM bms.user WHERE id=?";
        try(Connection conn = DBBanking.connect()){
            PreparedStatement stmt = conn.prepareStatement(SQL);
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                int tmpInt = rs.getInt(1);
                curr = currencies[tmpInt-1];
            }
        }catch(Exception e){
            throw new Exception("Something went wrong");
        }
        if(curr == null) throw new Exception("Something went wrong");
        return curr;
    }

    public static void insertBanking(User user, Banking banking) throws Exception{
        String SQL_1 = "INSERT INTO bms.banking (user,amount,date,type) VALUES(?,?,?,?)";
        String SQL_2 = "UPDATE bms.user SET balance=? where id=?";
        try(Connection conn = DBBanking.connect()){
            conn.setAutoCommit(false);

            PreparedStatement stmt = conn.prepareStatement(SQL_1);
            stmt.setInt(1,banking.userId());
            stmt.setDouble(2,banking.amount());
            stmt.setTimestamp(3,banking.date());
            stmt.setString(4,String.valueOf(banking.type()));
            stmt.execute();

            stmt = conn.prepareStatement(SQL_2);
            stmt.setDouble(1,user.getAmount());
            stmt.setInt(2,user.getId());
            stmt.execute();

            conn.commit();
            conn.setAutoCommit(true);
        }catch(Exception e){
            throw new Exception("Something went wrong");
        }

    }
    public static void insertTransaction(User sender, Transaction t) throws Exception{
        double senderNewAmount = sender.getAmount();
        double receiverNewAmount = Currency.convertFromTo(sender.getCurrency(),t.receiverCurr(),t.amount());
        String SQL_1 = "INSERT INTO bms.transaction (sender,receiver,amount,date) VALUES(?,?,?,?)";
        String SQL_2 = "UPDATE bms.user SET balance=? WHERE id=?";
        try(Connection conn = DBBanking.connect()){
            conn.setAutoCommit(false);

            PreparedStatement stmt = conn.prepareStatement(SQL_1);
            stmt.setInt(1,t.userId());
            stmt.setInt(2,t.receiverId());
            stmt.setDouble(3,t.amount());
            stmt.setTimestamp(4,t.date());
            stmt.execute();

            stmt = conn.prepareStatement(SQL_2);
            stmt.setDouble(1,senderNewAmount);
            stmt.setInt(2,sender.getId());
            stmt.execute();

            stmt = conn.prepareStatement(SQL_2);
            stmt.setDouble(1,receiverNewAmount);
            stmt.setInt(2,t.receiverId());
            stmt.execute();

            conn.commit();
            conn.setAutoCommit(true);
        }catch(Exception e){
            throw new Exception("Something went wrong");
        }
    }
}
