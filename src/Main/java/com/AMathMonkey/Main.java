package Main.java.com.AMathMonkey;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        Deque<CoinSlot> coinSlots = new ArrayDeque<>();
        Scanner sc = new Scanner(System.in);
        File file;
        double total = 0;

        System.out.println("Choose from the list of supported countries:");
        {
            int i = 1;
            for (MoneyTools.Country c : MoneyTools.Country.values()) {
                System.out.println(i++ + ")\t" + c);
            }
        }
        MoneyTools.Country country = MoneyTools.Country.values()[sc.nextInt()-1] ;

        System.out.println("Choose the type of currency you want to count:");
        {
            int i = 1;
            for (MoneyTools.CBB c : MoneyTools.CBB.values()) {
                System.out.println(i++ + ")\t" + c);
            }
        }
        MoneyTools.CBB cbb =  MoneyTools.CBB.values()[sc.nextInt()-1];



        switch (country){
            case CANADA:
                switch (cbb) {
                    case BILLS:
                        file = new File("src/CADb.txt");
                        break;
                    case COINS:
                        file = new File("src/CADc.txt");
                        break;
                    default:
                        file = new File("src/CADcb.txt");
                }
                break;
            case SINGAPORE:
                switch(cbb){
                    case BILLS:
                        file = new File("src/SGDb.txt");
                        break;
                    case COINS:
                        file = new File("src/SGDc.txt");
                        break;
                    default:
                        file = new File("src/SGDcb.txt");
                }
                break;
            default:
                file = new File("src/SGDc.txt");
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
            total += current.getTotal();
            coinSlots.addLast(current);
        }

        System.out.println("You have $" + total);
    }
}
