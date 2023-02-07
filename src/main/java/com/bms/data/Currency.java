package com.bms.data;

/**
 * Currency: Enum used for storing different currencies
 */
public enum Currency {
    EURO,RON,USD;

    /**
     * Method to get the currency's symbol
     * @return the symbol
     */
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

    /**
     * Helper method used to get to conversion of two currencies
     * @param c1 first currency
     * @param c2 second currency
     * @return conversion rate
     */
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

    /**
     * Method used to convert some amount from one currency to another
     * @param c1 the original currency
     * @param c2 the currency to convert to
     * @param amount the amount to convert
     * @return the converted amount
     */
    public static double convertFromTo(Currency c1, Currency c2, double amount){
        return (double)Math.round(amount*getConversionRate(c1,c2) * 100d) / 100d;
    }
}
