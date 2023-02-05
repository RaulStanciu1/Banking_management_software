package com.bms.data;

public enum Currency {
    EURO,RON,USD;
    public String getSymbol(){
        if (this == Currency.RON) {
            return "RON";
        }
        else if(this == Currency.EURO){
            return "€";
        }
        else {
            return "$";
        }
    }
    private static double getConversionRate(Currency c1, Currency c2){
        if(c1 == c2){
            return 1d;
        }
        if(c1 == Currency.RON && c2 == Currency.EURO){
            return 0.20;
        }else if(c1 == Currency.RON && c2 == Currency.USD){
            return 0.22;
        }else if(c1 == Currency.EURO && c2 == Currency.RON){
            return 4.90;
        }else if(c1 == Currency.EURO && c2 == Currency.USD){
            return 1.08;
        }
        else if(c1 == Currency.USD && c2 == Currency.RON){
            return 4.53;
        }else{
            return 0.92;
        }
    }
    public static double convertFromTo(Currency c1, Currency c2, double amount){
        return (double)Math.round(amount*getConversionRate(c1,c2) * 100d) / 100d;
    }
}
