package com.bms.data;

/**
 * Stats: A record used for storing daily data
 * @param bankingStats the number of deposits and withdrawals made that day
 * @param loanStats the number of loans made that day
 * @param transactionStats the number of transactions made that day
 */
public record Stats(int bankingStats, int loanStats, int transactionStats) {
}
