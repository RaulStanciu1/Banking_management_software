package com.bms.data;

import java.util.List;

public class Admin {
    private Stats dailyStats;
    private List<User> currentUsers;

    public Admin(){
        //TODO: Get Daily Stats and Current Users From the Database
    }
    public List<User> getCurrentUsers() {
        return currentUsers;
    }

    public Stats getDailyStats() {
        return dailyStats;
    }
}
