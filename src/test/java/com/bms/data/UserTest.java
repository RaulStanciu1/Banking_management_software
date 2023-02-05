package com.bms.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

class UserTest {

    @Test
    void getId() {
        User testUser1 = new User(1,"username1","BMS20username1",1,100);
        User testUser2 = new User(2,"username2","BMS20username2",2,200);
        User testUser3 = new User(3,"username3","BMS20username3",3,0);
        Assertions.assertEquals(1,testUser1.getId());
        Assertions.assertEquals(2,testUser2.getId());
        Assertions.assertEquals(3,testUser3.getId());
    }

    @Test
    void getUsername() {
        User testUser1 = new User(1,"username1","BMS20username1",1,100);
        User testUser2 = new User(2,"username2","BMS20username2",2,200);
        User testUser3 = new User(3,"username3","BMS20username3",3,0);
        Assertions.assertEquals("username1",testUser1.getUsername());
        Assertions.assertEquals("username2",testUser2.getUsername());
        Assertions.assertEquals("username3",testUser3.getUsername());
    }

    @Test
    void getCurrency() {
        User testUser1 = new User(1,"username1","BMS20username1",1,100);
        User testUser2 = new User(2,"username2","BMS20username2",2,200);
        User testUser3 = new User(3,"username3","BMS20username3",3,0);
        Assertions.assertEquals(Currency.EURO,testUser1.getCurrency());
        Assertions.assertEquals(Currency.RON,testUser2.getCurrency());
        Assertions.assertEquals(Currency.USD,testUser3.getCurrency());
    }

    @Test
    void getBankingCode() {
        User testUser1 = new User(1,"username1","BMS20username1",1,100);
        User testUser2 = new User(2,"username2","BMS20username2",2,200);
        User testUser3 = new User(3,"username3","BMS20username3",3,0);
        Assertions.assertEquals("BMS20username1",testUser1.getBankingCode());
        Assertions.assertEquals("BMS20username2",testUser2.getBankingCode());
        Assertions.assertEquals("BMS20username3",testUser3.getBankingCode());
    }

    @Test
    void getAmount() {
        User testUser1 = new User(1,"username1","BMS20username1",1,100);
        User testUser2 = new User(2,"username2","BMS20username2",2,200);
        User testUser3 = new User(3,"username3","BMS20username3",3,0);
        Assertions.assertEquals(100,testUser1.getAmount());
        Assertions.assertEquals(200,testUser2.getAmount());
        Assertions.assertEquals(0,testUser3.getAmount());
    }

    @Test
    void getBankingHistory() {
        User testUser = new User(1,"username1","BMS20username1",1,100);
        try{
            Assertions.assertEquals(testUser.getBankingHistory().size(),0);
        }catch(Exception e){
            fail("Failed at getBankingHistoryTest");
        }
    }
    @Test
    void getLoanHistory() {
        User testUser = new User(1,"username1","BMS20username1",1,100);
        Loan testLoan = new Loan(1,200,400,Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf("2024-09-01 09:01:15"));
        try{
            List<Loan> testList = testUser.getLoanHistory();
            testList.add(testLoan);
            Assertions.assertEquals(testList.size(),1);
        }catch(Exception e){
            fail("Failed at getLoanHistory Test");
        }
    }

    @Test
    void getTransactionHistory() {
        User testUser = new User(1,"username1","BMS20username1",1,100);
        try{
            Assertions.assertEquals(testUser.getTransactionHistory().size(),0);
        }catch(Exception e){
            fail("Failed at getTransactionHistory Test");
        }
    }

    @Test
    void createNewTransaction() {
        User testUser = new User(1,"username1","BMS20username1",1,100);
        Transaction testTransaction = new Transaction(1,2, Timestamp.valueOf(LocalDateTime.now()),Currency.EURO,100);
        Assertions.assertThrows(Exception.class,()-> testUser.createNewTransaction(0,""));
        Assertions.assertThrows(Exception.class,()-> testUser.createNewTransaction(200,""));
        Assertions.assertThrows(Exception.class,()-> testUser.createNewTransaction(50,"BMS20username1"));
        Assertions.assertThrows(Exception.class,()-> testUser.createNewTransaction(50,"BMS20username2"));
        try{
            Assertions.assertEquals(testTransaction.receiverId(),testUser.createNewTransaction(20,"RO20BMS@administrator").receiverId());
        }catch(Exception e){
            fail("Failed at createNewTransaction Test");
        }
    }

    @Test
    void createNewBanking() {
        User testUser = new User(1,"username1","BMS20username1",1,100);
        Assertions.assertThrows(Exception.class,()-> testUser.createNewBanking(BankingType.DEPOSIT,0));
        Assertions.assertThrows(Exception.class,()-> testUser.createNewBanking(BankingType.DEPOSIT,10000001));
        Assertions.assertDoesNotThrow(()->testUser.createNewBanking(BankingType.DEPOSIT,200));
        Assertions.assertThrows(Exception.class,()-> testUser.createNewBanking(BankingType.WITHDRAW,0));
        Assertions.assertThrows(Exception.class,()-> testUser.createNewBanking(BankingType.WITHDRAW,10000001));
        Assertions.assertDoesNotThrow(()->testUser.createNewBanking(BankingType.WITHDRAW,50));
    }
}