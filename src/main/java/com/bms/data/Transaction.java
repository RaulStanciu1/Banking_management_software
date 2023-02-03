package com.bms.data;

import java.sql.Timestamp;

public record Transaction(int userId, int receiverId, Timestamp date, double amount) {
}
