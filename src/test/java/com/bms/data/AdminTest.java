package com.bms.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;


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
    void getCurrentUsers() {
        try{
            Assertions.assertEquals(4,adminTest.getCurrentUsers().size());
        }catch(Exception e){
            fail("Failed at getCurrentUsers Test");
        }
    }

    @Test
    void getDailyStats() {
        Assertions.assertDoesNotThrow(()->adminTest.getDailyStats());
    }
}