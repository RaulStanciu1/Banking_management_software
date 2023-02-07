package com.bms.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;



class AdminTest {
    private static final Admin adminTest = new Admin(2,"administrator");

    @Test
    void getId() {
        Assertions.assertEquals(adminTest.getId(),2);
    }

    @Test
    void getUsername() {
        Assertions.assertEquals(adminTest.getUsername(),"administrator");
    }

    @Test
    void getDailyStats() {
        Assertions.assertDoesNotThrow(adminTest::getDailyStats);
    }

    @Test
    void getTotalStats(){
        Assertions.assertDoesNotThrow(adminTest::getTotalStats);
    }

    @Test
    void getAllUsers(){
        Assertions.assertDoesNotThrow(Admin::getAllUsers);
    }

}