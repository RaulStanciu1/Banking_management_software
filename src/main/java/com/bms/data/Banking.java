package com.bms.data;

import java.time.LocalDateTime;

public record Banking(int userId, BankingType type, double amount, LocalDateTime date) {
}
