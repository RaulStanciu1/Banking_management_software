package com.bms.data;

import java.sql.Timestamp;

/**
 * Loan: A record used for storing information related to a loan
 * @param userId the user id
 * @param requestAmount the amount requested
 * @param requestLeft the amount left for the user to pay
 * @param date the loan date
 * @param nextPayment the date of the next payment
 */
public record Loan(int userId, double requestAmount, double requestLeft, Timestamp date, Timestamp nextPayment) {
}
