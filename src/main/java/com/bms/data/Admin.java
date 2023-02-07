package com.bms.data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Admin: A class used to store every information relevant for an administrator
 */
public class Admin extends UserInfo{
    final static String URL = "jdbc:mysql://localhost:3306/bms";
    final static String USER = "bms_admin";
    final static String PASS = "P@ssword12";
    private Stats dailyStats;
    private TotalStats totalStats;

    /**
     * Default constructor
     * @param userId the user id
     * @param username the username
     */
    public Admin(int userId, String username){
        super(userId,username);
        dailyStats = null;
        totalStats = null;
    }
    /**
     * Daily stats getter(if doesn't exist get it from database)
     * @return daily stats
     * @throws Exception exception if connection failed
     */
    public Stats getDailyStats() throws Exception{
        if(dailyStats == null){
            String SQL="SELECT * from bms.daily_stats";
            try(Connection conn = DriverManager.getConnection(URL,USER,PASS)){
                PreparedStatement stmt = conn.prepareStatement(SQL);
                ResultSet rs = stmt.executeQuery();
                if(rs.next()){
                    this.dailyStats = new Stats(rs.getInt("banking_stats"),rs.getInt("loan_stats"),
                            rs.getInt("transaction_stats"));
                }
            }catch(Exception e){
                throw new Exception("Something went wrong");
            }
        }
        return dailyStats;
    }

    /**
     * Total stats getter(if doesn't exist extract from the database)
     * @return the total stats
     * @throws Exception exception if connection failed
     */
    public TotalStats getTotalStats() throws Exception {
        if(totalStats == null){
            String SQL = "SELECT * from bms.total_stats";
            try(Connection conn = DriverManager.getConnection(URL,USER,PASS)){
                PreparedStatement stmt = conn.prepareStatement(SQL);
                ResultSet rs = stmt.executeQuery();
                if(rs.next()){
                    this.totalStats = new TotalStats(
                            rs.getInt("deposits"),
                            rs.getInt("withdraws"),
                            rs.getInt("loans"),
                            rs.getInt("transactions"),
                            rs.getDouble("euro"),
                            rs.getDouble("ron"),
                            rs.getDouble("usd"),
                            rs.getInt("users")
                    );
                }
            }catch(Exception e){
                throw new Exception("Something went wrong");
            }
        }
        return totalStats;
    }
    /**
     * Method used to get all the current users from the database
     * @return list of all users(except admin)
     * @throws Exception Exception if connection failed
     */
    public static List<User> getAllUsers() throws Exception{
        String SQL="SELECT * FROM bms.user WHERE id!=2";
        List<User> users = new ArrayList<>();
        try(Connection conn=DriverManager.getConnection(URL,USER,PASS)){
            PreparedStatement stmt = conn.prepareStatement(SQL);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("banking_code"),
                        rs.getInt("currency"),
                        rs.getDouble("balance")
                ));
            }
        }catch(Exception e){
            throw new Exception("Something went wrong");
        }
        return users;
    }
}
