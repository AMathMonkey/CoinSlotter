package com.AMathMonkey;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
	// write your code here
        Deque<CoinSlot> coinSlots = new ArrayDeque<>();
        Scanner sc = new Scanner(System.in);
        File file;
        double total = 0;

        System.out.println("Choose from the list of supported countries:");
        {
            int i = 1;
            for (MoneyTools.Countries c : MoneyTools.Countries.values()) {
                System.out.println(i++ + ")\t" + c);
            }
        }
        MoneyTools.Countries country = MoneyTools.Countries.values()[sc.nextInt()-1] ;

        System.out.println("Choose the type of currency you want to count:");
        {
            int i = 1;
            for (MoneyTools.CoinsBillsOrBoth c : MoneyTools.CoinsBillsOrBoth.values()) {
                System.out.println(i++ + ")\t" + c);
            }
        }
        MoneyTools.CoinsBillsOrBoth cbb =  MoneyTools.CoinsBillsOrBoth.values()[sc.nextInt()-1];



        switch (country){
            case CANADA:
                switch (cbb) {
                    case BILLS:
                        file = new File("src/billsCAD.txt");
                        break;
                    case COINS:
                        file = new File("src/coinsCAD.txt");
                        break;
                    default:
                        file = new File("src/coinsandbillsCAD.txt");
                }
                break;
            case SINGAPORE:
                switch(cbb){
                    case BILLS:
                        file = new File("src/billsSGD.txt");
                        break;
                    case COINS:
                        file = new File("src/coinsSGD.txt");
                        break;
                    default:
                        file = new File("src/coinsandbillsSGD.txt");
                }
                break;
            default:
                file = new File("src/coinsSGD.txt");
        }

        Scanner fileSc = new Scanner(file);

        while(fileSc.hasNext()){
            String[] line  = fileSc.nextLine().split(" ");
            coinSlots.addLast(new CoinSlot(Double.parseDouble(line[0]), Boolean.parseBoolean(line[1])));
        }

        for(int i = 0; i < coinSlots.size(); i++){
            CoinSlot current = coinSlots.removeFirst();
            double currentValue = current.getValue();
            boolean currentIsCoin = current.isCoin();

            if(current.getValue() < 1){
                System.out.print("How many " + (int)(currentValue*100) + " cent " + (currentIsCoin? "coins":"bills") +" do you have? ");
            }
            else{
                System.out.print("How many " + (int)(currentValue) + " dollar " + (currentIsCoin? "coins":"bills") +" do you have? ");
            }

            current.setNumCoins(sc.nextInt());

            coinSlots.addLast(current);
        }
        for(int i = 0; i < coinSlots.size(); i++){
            CoinSlot current = coinSlots.removeFirst();
            double currentValue = current.getValue();
            int currentNumCoins = current.getNumCoins();
            total += current.getTotal();
            coinSlots.addLast(current);
        }

        System.out.println("You have $" + total);
    }
}