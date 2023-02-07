package com.bms.data;

import java.sql.Timestamp;

/**
 * Banking: A record used for storing information relating to a banking service(DEPOSIT or WITHDRAWAL)
 * @param userId the user id
 * @param type the type (DEPOSIT OR WITHDRAWAL)
 * @param amount the amount
 * @param date the date
 */
public record Banking(int userId, BankingType type, double amount, Timestamp date) {
}
