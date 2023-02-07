package com.bms.routine;

import com.bms.data.Loan;

import java.sql.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * LoanRoutine: A Class used for checking every due payments on every loan and automatically extracting the balance from the user
 */
public class LoanRoutine {
    final static String URL = "jdbc:mysql://localhost:3306/bms";
    final static String USER = "bms_admin";
    final static String PASS = "P@ssword12";

    /**
     * Method to search the database for any due loans
     * @return a list of all the due loans
     * @throws Exception exception if connection failed
     */
    public static List<LoanRecord> lookForDueLoans() throws Exception{
        List<LoanRecord> loans = new ArrayList<>();
        String SQL="SELECT * from bms.loan WHERE next_payment<CURRENT_TIMESTAMP AND request_left>0";
        try(Connection conn = DriverManager.getConnection(URL,USER,PASS)){
            PreparedStatement stmt = conn.prepareStatement(SQL);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                loans.add(new LoanRecord(
                   rs.getInt("id"),
                        new Loan(
                                rs.getInt("user"),
                                rs.getDouble("request_amount"),
                                rs.getDouble("request_left"),
                                rs.getTimestamp("date"),
                                rs.getTimestamp("next_payment")
                        )
                ));
            }
        }catch(Exception e){
            throw new Exception("Database Query Failed, Terminating Routine");
        }
        return loans;
    }

    /**
     * Method used to update the payment on every found loan
     * @param l the list of loans
     * @throws Exception exception if connection failed
     */
    public static void takePayment(List<LoanRecord> l) throws Exception{
        String SQL_1 = "UPDATE bms.loan SET next_payment = ?";
        String SQL_USER_BALANCE="SELECT balance from bms.user WHERE id=?";
        String SQL_2 = "UPDATE bms.user SET balance = ? WHERE id=?";
        String SQL_REQUEST_LEFT="SELECT request_left from bms.loan WHERE id=?";
        String SQL_3 = "UPDATE bms.loan SET request_left=? WHERE id=?";
        try{
            for(LoanRecord lr:l){
                try(Connection conn = DriverManager.getConnection(URL,USER,PASS)){
                    conn.setAutoCommit(false);

                    PreparedStatement stmt = conn.prepareStatement(SQL_1);
                    stmt.setTimestamp(1,Timestamp.valueOf(lr.loan().nextPayment().toLocalDateTime().plusMonths(1)));
                    stmt.execute();

                    stmt = conn.prepareStatement(SQL_USER_BALANCE);
                    stmt.setInt(1,lr.loan().userId());
                    ResultSet rs = stmt.executeQuery();
                    if(!rs.next()){
                        throw new Exception("Something went wrong");
                    }
                    double balance = rs.getDouble("balance");
                    balance -= (lr.loan().requestAmount()/10);
                    balance = (double) Math.round(balance*100)/100;

                    stmt = conn.prepareStatement(SQL_2);
                    stmt.setDouble(1,balance);
                    stmt.setInt(2,lr.loan().userId());
                    stmt.execute();

                    stmt = conn.prepareStatement(SQL_REQUEST_LEFT);
                    stmt.setInt(1,lr.loanId());
                    rs = stmt.executeQuery();
                    if(!rs.next()){
                        throw new Exception("Something went wrong");
                    }
                    double requestLeft = rs.getDouble("request_left");
                    requestLeft -= (lr.loan().requestAmount()/10);
                    requestLeft = (double) Math.round(requestLeft *100) /100;


                    stmt= conn.prepareStatement(SQL_3);
                    stmt.setDouble(1,requestLeft);
                    stmt.setDouble(2,lr.loanId());
                    stmt.execute();

                    conn.commit();
                    conn.setAutoCommit(true);
                }catch(Exception e){
                    throw new Exception(e);
                }
            }
        }catch(Exception e){
            throw new Exception("Database Transaction failed, Terminating Routine");
        }
    }

    /**
     * Starting point of the Routine
     * @param args arguments(none used)
     */
    public static void main(String[] args){
        try{
            do{
                System.out.println("Looking for due loans");
                List<LoanRecord> loans = lookForDueLoans();
                if(loans.size()!=0) {
                    System.out.println("Found some loans... Taking payments...");
                    takePayment(loans);
                }
                System.out.println("Successfully modified "+loans.size()+" loans");
                loans.clear();
                Thread.sleep(Duration.ofDays(1));
            }while(true);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
}
