package com.bms.data;

/**
 * TotalStats: A record used for storing all the information of the bms activity
 * @param deposits the number of deposits
 * @param withdraws the number of withdrawals
 * @param loans the number of loans
 * @param transactions the number of transactions
 * @param euro the accumulative amount of euro in the user's accounts
 * @param ron the accumulative amount of ron in the user's accounts
 * @param usd the accumulative amount of usd in the user's accounts
 * @param users the current number of users
 */
public record TotalStats(int deposits,int withdraws, int loans, int transactions, double euro, double ron, double usd, int users) {
}
