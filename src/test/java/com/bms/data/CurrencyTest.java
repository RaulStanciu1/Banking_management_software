package com.bms.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyTest {

    @Test
    void convertFromTo() {
        Assertions.assertEquals(4.9,Currency.convertFromTo(Currency.EURO,Currency.RON,1));
        Assertions.assertEquals(1,Currency.convertFromTo(Currency.EURO,Currency.EURO,1));
        Assertions.assertEquals(1.08,Currency.convertFromTo(Currency.EURO,Currency.USD,1));

        Assertions.assertEquals(75.95,Currency.convertFromTo(Currency.EURO,Currency.RON,15.5));
        Assertions.assertEquals(15.5,Currency.convertFromTo(Currency.EURO,Currency.EURO,15.5));
        Assertions.assertEquals(16.74,Currency.convertFromTo(Currency.EURO,Currency.USD,15.5));

        Assertions.assertEquals(15.5,Currency.convertFromTo(Currency.RON,Currency.RON,15.5));
        Assertions.assertEquals(3.1,Currency.convertFromTo(Currency.RON,Currency.EURO,15.5));
        Assertions.assertEquals(3.41,Currency.convertFromTo(Currency.RON,Currency.USD,15.5));

        Assertions.assertEquals(70.22,Currency.convertFromTo(Currency.USD,Currency.RON,15.5));
        Assertions.assertEquals(14.26,Currency.convertFromTo(Currency.USD,Currency.EURO,15.5));
        Assertions.assertEquals(15.5,Currency.convertFromTo(Currency.USD,Currency.USD,15.5));
    }

    @Test
    void getSymbol() {
        Assertions.assertEquals("RON",Currency.RON.getSymbol());
        Assertions.assertEquals("$",Currency.USD.getSymbol());
        Assertions.assertEquals("€",Currency.EURO.getSymbol());
    }
}