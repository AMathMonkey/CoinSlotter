package com.AMathMonkey;

import javafx.stage.Stage;

import java.text.DecimalFormat;

class MoneyTools {
    static double round(double value) {
        long factor = (long) Math.pow(10, 2);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public enum Countries{
        CANADA, SINGAPORE
    }

    public enum CoinsBillsOrBoth {
        COINS, BILLS, COINS_AND_BILLS
    }

    public static boolean isNumeric(String str)
    {
        try
        {
            int i = Integer.parseInt(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

    public static Countries globalCountry;
    public static CoinsBillsOrBoth globalCBB;
    public static Stage globalStage;

    public static DecimalFormat df = new DecimalFormat("#0.00");
}
