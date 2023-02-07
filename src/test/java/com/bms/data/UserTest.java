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
            List<Banking> testList = testUser.getBankingHistory();
            Assertions.assertEquals(0,testList.size());
        }catch(Exception e){
            fail("Failed at getBankingHistory test");
        }
    }
    @Test
    void getLoanHistory() {
        User testUser = new User(1,"username1","BMS20username1",1,100);
        try{
            List<Loan> testList = testUser.getLoanHistory();
            Assertions.assertEquals(0,testList.size());
        }catch(Exception e){
            fail("Failed at getBankingHistory test");
        }
    }

    @Test
    void getTransactionHistory() {
        User testUser = new User(1,"username1","BMS20username1",1,100);
        try{
            List<Transaction> testList = testUser.getTransactionHistory();
            Assertions.assertEquals(0,testList.size());
        }catch(Exception e){
            fail("Failed at getBankingHistory test");
        }
    }

    @Test
    void createNewTransaction() {

        User testUser = new User(1,"username1","BMS20username1",1,100);
        Assertions.assertThrows(Exception.class,()-> testUser.createNewTransaction(0,""));
        Assertions.assertThrows(Exception.class,()-> testUser.createNewTransaction(200,""));
        Assertions.assertThrows(Exception.class,()-> testUser.createNewTransaction(50,"BMS20username1"));
        Assertions.assertThrows(Exception.class,()-> testUser.createNewTransaction(50,"BMS20username2"));
        Transaction testTransaction = new Transaction(1,2, Timestamp.valueOf(LocalDateTime.now()),Currency.EURO,100);
        Assertions.assertEquals(1,testTransaction.userId());
        Assertions.assertEquals(2,testTransaction.receiverId());
        Assertions.assertEquals(Currency.EURO,testTransaction.receiverCurr());
        Assertions.assertEquals(100,testTransaction.amount());
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
        Banking testBanking = new Banking(1,BankingType.DEPOSIT,100,Timestamp.valueOf(LocalDateTime.now()));
        Assertions.assertEquals(1,testBanking.userId());
        Assertions.assertEquals(100,testBanking.amount());
        Assertions.assertEquals(BankingType.DEPOSIT,testBanking.type());
    }

    @Test
    void createNewLoan() {
        User testUser = new User(1,"username1","BMS20username1",1,100);
        Assertions.assertThrows(Exception.class, ()->testUser.createNewLoan(0));
        Assertions.assertThrows(Exception.class, ()->testUser.createNewLoan(100));
        Assertions.assertDoesNotThrow(() -> testUser.createNewLoan(1000));
        Assertions.assertThrows(Exception.class, ()->testUser.createNewLoan(10_000_001));
        Loan testLoan = new Loan(1,100,200,Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.MAX));
        Assertions.assertEquals(1,testLoan.userId());
        Assertions.assertEquals(100,testLoan.requestAmount());
        Assertions.assertEquals(200,testLoan.requestLeft());
        Assertions.assertEquals(Timestamp.valueOf(LocalDateTime.MAX),testLoan.nextPayment());
    }
}