package com.bms.data;

import java.util.List;

public record Stats(List<Banking> bankingStats, List<Loan> loanStats, List<Transaction> transactionStats) {
}
