package com.bms.auth;


import com.bms.data.Admin;
import com.bms.data.Currency;
import com.bms.data.User;

import java.sql.*;

/**
 * DBAuth: A class used for any database queries regarding authentication
 */
public class DBAuth {
    final static String URL = "jdbc:mysql://localhost:3306/bms";
    final static String USER = "bms_admin";
    final static String PASS = "P@ssword12";

    /**
     * Helper method for connection to the database
     * @return connection
     * @throws SQLException exception
     */
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    /**
     *  Creating the banking code
     * @param username the username
     * @return the banking code
     */
    public static String createBankingCode(String username){
        return "RO20BMS@"+username;
    }

    /**
     * Method to get the password stored in the database from a user
     * @param username the username
     * @return the stored password
     * @throws Exception exception if could not connect or retrieve
     */
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

    /**
     * Method used to register a new user to the database
     * @param username the username
     * @param password the password
     * @param currency the currency
     * @throws Exception exception if connection failed
     */
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

    /**
     * Method to check if a username exists in the database
     * @param username the username
     * @return true if the username already exists, false otherwise
     * @throws Exception exception if connection failed
     */
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

    /**
     * Method to get a user object from the database
     * @param username the username of the user
     * @return a user object
     * @throws Exception exception if connection failed or username doesn't exist in the database
     */
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
            throw new Exception("Something went wrong");
        }
        if(user == null) throw new Exception("Something went wrong");
        return user;
    }

    /**
     * Method to return an admin object from the database
     * @param username the username
     * @return Admin object
     * @throws Exception exception if connection failed
     */
    public static Admin getAdmin(String username) throws Exception {
        Admin user=null;
        String SQL="SELECT id,username FROM bms.user WHERE username=?";
        try(Connection conn = DBAuth.connect()){
            PreparedStatement stmt = conn.prepareStatement(SQL);
            stmt.setString(1,username);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                user = new Admin(rs.getInt("id"),rs.getString("username"));
            }
        }catch(Exception e){
            throw new Exception("Something went wrong");
        }
        if(user == null) throw new Exception("Something went wrong");
        return user;
    }
}
