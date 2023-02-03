package com.bms.auth;


import com.bms.data.Currency;
import com.bms.data.User;

import java.sql.*;

public class DBAuth {
    final static String URL = "jdbc:mysql://localhost:3306/bms";
    final static String USER = "bms_admin";
    final static String PASS = "P@ssword";
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
    private static String createBankingCode(String username){
        return "RO20BMS@"+username;
    }
    public static String getStoredPassword(String username) throws Exception{
        String pass = null;
        String SQL = "SELECT password FROM bms.user WHERE username = ?";
        try(Connection conn = DBAuth.connect()){
            PreparedStatement stmt = conn.prepareStatement(SQL);
            stmt.setString(1,username);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                pass = rs.getString(1);
            }
        }catch(Exception e){
            throw new Exception("Something went wrong");
        }
        if(pass == null) throw new Exception("Could not retrieve password");
        return pass;
    }
    public static void registerNewUser(String username, String password, Currency currency) throws Exception {
        String hashedPassword = Password.generateStrongPasswordHash(password);
        String bankingCode = createBankingCode(username);
        int currStr = currency.ordinal()+1;
        String SQL = "INSERT INTO bms.user (username,password,currency,banking_code,balance) VALUES(?,?,?,?,0)";
        try(Connection conn = DBAuth.connect()){
            PreparedStatement stmt = conn.prepareStatement(SQL);
            stmt.setString(1,username);
            stmt.setString(2,hashedPassword);
            stmt.setInt(3,currStr);
            stmt.setString(4,bankingCode);
            stmt.execute();
        }catch(Exception e){
            throw new Exception("Something went wrong");
        }
    }
    public static boolean checkUsernameExists(String username) throws Exception{
        boolean usernameExists = true;
        String SQL = "SELECT username FROM bms.user WHERE username = ?";
        try(Connection conn = DBAuth.connect()){
            PreparedStatement stmt = conn.prepareStatement(SQL);
            stmt.setString(1,username);
            ResultSet rs = stmt.executeQuery();
            if(!rs.next()){
                usernameExists=false;
            }
        }catch(Exception e){
            throw new Exception("Something went wrong");
        }
        return usernameExists;
    }
    public static User getUser(String username)throws Exception{
        User user=null;
        String SQL="SELECT id,username,banking_code,currency,balance FROM bms.user WHERE username=?";
        try(Connection conn = DBAuth.connect()){
            PreparedStatement stmt = conn.prepareStatement(SQL);
            stmt.setString(1,username);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                user = new User(rs.getInt("id"),rs.getString("username"),
                        rs.getString("banking_code"),rs.getInt("currency"),
                        rs.getDouble("balance"));
            }
        }catch(Exception e){
            System.out.println(e);
            throw new Exception("Something went wrong");
        }
        if(user == null) throw new Exception("Something went wrong");
        return user;
    }
}
