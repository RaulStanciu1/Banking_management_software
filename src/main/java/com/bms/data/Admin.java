package com.bms.data;

import java.util.List;

public class Admin extends UserInfo{
    private Stats dailyStats;
    private List<User> currentUsers;

    public Admin(int userId, String username){
        super(userId,username);
    }
    public List<User> getCurrentUsers() {
        //TODO: Get current users from db
        return currentUsers;
    }

    public Stats getDailyStats() {
        //TODO: Get daily stats from db
        return dailyStats;
    }
}
