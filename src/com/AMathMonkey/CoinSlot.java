package com.AMathMonkey;

public class CoinSlot {
    private double value;
    private int numCoins;
    private boolean isCoin;

    /**
     * CoinSlot constructor
     * @param value the value of the type of coin that fits in this slot
     * @param isCoin true if this slot holds coins, false if it holds bills
     */
    CoinSlot(double value, boolean isCoin){
        if(value <= 0) throw new IllegalArgumentException();

        this.value = value;
        numCoins = 0;
        this.isCoin = isCoin;
    }

    void addCoin(){
        numCoins++;
    }
    void removeCoin(){
        if(numCoins > 0) {
            numCoins--;
        }
    }

    void setNumCoins(int numCoins){
        if(numCoins < 0) throw new IllegalArgumentException();
        this.numCoins = numCoins;
    }

    int getNumCoins(){
        return numCoins;
    }

    double getValue(){
        return value;
    }

    double getTotal(){
        return MoneyTools.round(value * numCoins);
    }

    boolean isCoin(){
        return isCoin;
    }

    public String toString(){
        return "CoinSlot holding " + numCoins + " " + value + " " + (isCoin? "coins" : "bills") + " with a total value of " + getTotal();
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass() == getClass() && obj.toString().equals(toString());
    }
}
