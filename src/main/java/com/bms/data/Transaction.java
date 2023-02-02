package com.bms.data;

import java.time.LocalDateTime;

public record Transaction(int userId, int receiverId, LocalDateTime date, double amount) {
}
