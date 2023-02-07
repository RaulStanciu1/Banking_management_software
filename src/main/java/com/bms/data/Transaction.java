package com.bms.data;

import java.sql.Timestamp;

/**
 * Transaction: A record used to store information related to a transaction
 * @param userId the sender id
 * @param receiverId the id of the receiver
 * @param date the date
 * @param receiverCurr the receiver's currency
 * @param amount the amount to be sent
 */
public record Transaction(int userId, int receiverId, Timestamp date, Currency receiverCurr,double amount) {
}
