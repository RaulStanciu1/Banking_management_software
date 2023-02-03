package com.bms.data;

import java.sql.Timestamp;

public record Banking(int userId, BankingType type, double amount, Timestamp date) {
}
