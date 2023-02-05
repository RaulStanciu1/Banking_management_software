package com.bms.data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Admin extends UserInfo{
    final static String URL = "jdbc:mysql://localhost:3306/bms";
    final static String USER = "bms_admin";
    final static String PASS = "P@ssword";
    private Stats dailyStats;

    public Admin(int userId, String username){
        super(userId,username);
        dailyStats = null;
    }
    public List<User> getCurrentUsers() throws Exception{
        List<User>currentUsers = new ArrayList<>();
        String SQL = "SELECT * FROM bms.user";
        try(Connection conn = DriverManager.getConnection(URL,USER,PASS)){
            PreparedStatement stmt = conn.prepareStatement(SQL);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                currentUsers.add(
                        new User(
                            rs.getInt("id"),rs.getString("username"),
                                rs.getString("banking_code"),rs.getInt("currency"),
                                rs.getDouble("balance")
                        )
                );
            }
        }catch(Exception e){
            throw new Exception("Something went wrong");
        }
        return currentUsers;
    }

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
}
