package com.bms.data;

import java.sql.Timestamp;

public record Loan(int userId, double requestAmount, double requestLeft, Timestamp date, Status status) {
}
