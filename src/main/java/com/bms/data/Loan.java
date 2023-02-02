package com.bms.data;

import java.time.LocalDateTime;

public record Loan(int userId, double requestAmount, double requestLeft, LocalDateTime date, Status status) {
}
