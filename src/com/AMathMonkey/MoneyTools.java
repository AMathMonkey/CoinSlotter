package com.AMathMonkey;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.TreeMap;

class MoneyTools {
    static double round(double value) {
        long factor = (long) Math.pow(10, 2);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    /**
     * This is an enum to represent the list of supported countries
     */
    public enum Country {

        CANADA, SINGAPORE;
    }
    static Map<String, Country> countryMap(){
        Map<String, Country> countryMap = new TreeMap<>();
        countryMap.put("Canada/USA", Country.CANADA);
        countryMap.put("Singapore", Country.SINGAPORE);

        return countryMap;
    }

    /**
     * This is an enum to represent the choice between using only coins, only bills, or both
     * In V1 of this program, it was called CoinsBillsOrBoth, thus CBB
     */
    public enum CBB {
        COINS, BILLS, COINS_AND_BILLS;

    }
    static Map<String, CBB> cbbMap(){
        Map<String, CBB> currencyMap = new TreeMap<>();
        currencyMap.put("Coins", CBB.COINS);
        currencyMap.put("Bills", CBB.BILLS);
        currencyMap.put("Coins & Bills", CBB.COINS_AND_BILLS);

        return currencyMap;
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





    static Country globalCountry;
    static CBB globalCBB;
    static String globalCountryString;
    static String globalCBBString;

    static DecimalFormat df = new DecimalFormat("#0.00");
}
