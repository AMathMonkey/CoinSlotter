package Main.java.com.AMathMonkey;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static Main.java.com.AMathMonkey.MoneyTools.globalCBBString;
import static Main.java.com.AMathMonkey.MoneyTools.globalCountryString;

public class MainWindow extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    private MoneyTools.Country c = MoneyTools.globalCountry;
    private MoneyTools.CBB cbb = MoneyTools.globalCBB;


    private int search(List<Button> arr, Object find) {
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).equals(find)) return i;
        }
        return -1;
    }

    @Override
    public void start(Stage stage) throws IOException {

        Pane mainPane = new Pane();
        Label total = new Label("$0.00");
        mainPane.setBackground(new Background(new BackgroundImage(new Image("Main/resources/Background.png"),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));

        List<CoinSlot> coinSlots = new ArrayList<>();
        InputStream file;
        InputStream file2forCB = null;
        switch (c) {
            case CANADA:
                switch (cbb) {
                    case BILLS:
                        file = ClassLoader.getSystemResourceAsStream("Main/resources/CADb.txt");
                        break;
                    case COINS:
                        file = ClassLoader.getSystemResourceAsStream("Main/resources/CADc.txt");
                        break;
                    default:
                        file = ClassLoader.getSystemResourceAsStream("Main/resources/CADc.txt");
                        file2forCB = ClassLoader.getSystemResourceAsStream("Main/resources/CADb.txt");
                }
                break;
            case SINGAPORE:
                switch (cbb) {
                    case BILLS:
                        file = ClassLoader.getSystemResourceAsStream("Main/resources/SGDb.txt");
                        break;
                    case COINS:
                        file = ClassLoader.getSystemResourceAsStream("Main/resources/SGDc.txt");
                        break;
                    default:
                        file = ClassLoader.getSystemResourceAsStream("Main/resources/SGDc.txt");
                        file2forCB = ClassLoader.getSystemResourceAsStream("Main/resources/SGDb.txt");
                }
                break;
            default:
                file = ClassLoader.getSystemResourceAsStream("Main/resources/SGDc.txt");
        }


        Scanner fileSc = new Scanner(file);


        while (fileSc.hasNext()) {
            String[] line = fileSc.nextLine().split(" ");
            coinSlots.add(new CoinSlot(Double.parseDouble(line[0]), Boolean.parseBoolean(line[1])));
        }
        if(file2forCB != null){
            fileSc = new Scanner(file2forCB);
            while (fileSc.hasNext()) {
                String[] line = fileSc.nextLine().split(" ");
                coinSlots.add(new CoinSlot(Double.parseDouble(line[0]), Boolean.parseBoolean(line[1])));
            }
        }

        int numOfRows = coinSlots.size();

        List<Label> valueLabels = new ArrayList<>();
        List<TextField> textFields = new ArrayList<>();
        List<Button> plusButtons = new ArrayList<>();
        List<Button> minusButtons = new ArrayList<>();
        List<Label> totalLabels = new ArrayList<>();

        for (CoinSlot coinSlot : coinSlots) {
            if (coinSlot.isCoin()) {
                if (coinSlot.getValue() < 1) {
                    valueLabels.add(new Label(Integer.toString((int) (coinSlot.getValue() * 100)) + "¢ coins"));
                } else {
                    valueLabels.add(new Label("$" + Integer.toString((int) coinSlot.getValue()) + " coins"));
                }
            } else {
                valueLabels.add(new Label("$" + Integer.toString((int) coinSlot.getValue()) + " bills"));
            }

            textFields.add(new TextField());
            plusButtons.add(new Button());
            minusButtons.add(new Button());
            totalLabels.add(new Label());
        }
        int j = 25;

        for (int i = 0; i < valueLabels.size(); i++) {
            //Makes labels for different coin values
            valueLabels.get(i).relocate(25, j + 5);
            valueLabels.get(i).setTextFill(Paint.valueOf("white"));
            mainPane.getChildren().add(valueLabels.get(i));

            //Makes text fields for each label
            textFields.get(i).relocate(150, j);
            textFields.get(i).setOnKeyReleased(event12 -> {
                double tempTotal = 0;
                for (int i2 = 0; i2 < coinSlots.size(); i2++) {
                    if (MoneyTools.isNumeric(textFields.get(i2).getText())) {
                        coinSlots.get(i2).setNumCoins(Integer.parseInt(textFields.get(i2).getText()));
                        totalLabels.get(i2).setText("$" + MoneyTools.df.format(coinSlots.get(i2).getTotal()));
                    } else if (textFields.get(i2).getText().equals("")) {
                        coinSlots.get(i2).setNumCoins(0);
                        totalLabels.get(i2).setText("$" + MoneyTools.df.format(coinSlots.get(i2).getTotal()));
                    }
                    tempTotal += coinSlots.get(i2).getTotal();
                }
                total.setText("$" + MoneyTools.df.format(tempTotal));
            });
            mainPane.getChildren().add(textFields.get(i));

            //Makes + buttons for each label, and can search for the text field corresponding to each button
            plusButtons.get(i).setText("+");
            plusButtons.get(i).relocate(300, j);
            plusButtons.get(i).setOnAction(event1 -> {
                int i1 = search(plusButtons, event1.getSource());
                String tempText = textFields.get(i1).getText();
                if (MoneyTools.isNumeric(tempText)) {
                    coinSlots.get(i1).addCoin();
                    total.setText("$" + MoneyTools.df.format(Double.parseDouble(total.getText().replaceAll("(?<=\\d),(?=\\d)|\\$", ""))
                            + coinSlots.get(i1).getValue()));
                    totalLabels.get(i1).setText("$" + MoneyTools.df.format(coinSlots.get(i1).getTotal()));
                    textFields.get(i1).setText(Integer.toString(coinSlots.get(i1).getNumCoins()));
                } else if (tempText.equals("")) {
                    textFields.get(i1).setText("1");
                    coinSlots.get(i1).addCoin();
                    totalLabels.get(i1).setText("$" + MoneyTools.df.format(coinSlots.get(i1).getTotal()));
                    total.setText("$" + MoneyTools.df.format(Double.parseDouble(total.getText().replaceAll("(?<=\\d),(?=\\d)|\\$", ""))
                            + (coinSlots.get(i1).getTotal())));
                }
            });
            mainPane.getChildren().add(plusButtons.get(i));

            minusButtons.get(i).setText("-");
            minusButtons.get(i).relocate(325, j);
            minusButtons.get(i).setOnAction(event1 -> {
                int i3 = search(minusButtons, event1.getSource());
                String tempText = textFields.get(i3).getText();
                if (MoneyTools.isNumeric(tempText) && Integer.parseInt(tempText) > 0) {
                    coinSlots.get(i3).removeCoin();
                    total.setText("$" + MoneyTools.df.format(Double.parseDouble(total.getText().replaceAll("(?<=\\d),(?=\\d)|\\$", ""))
                            - coinSlots.get(i3).getValue()));
                    totalLabels.get(i3).setText("$" + MoneyTools.df.format(coinSlots.get(i3).getTotal()));
                    textFields.get(i3).setText(Integer.toString(coinSlots.get(i3).getNumCoins()));
                } else {
                    textFields.get(i3).setText("0");
                }
            });
            mainPane.getChildren().add(minusButtons.get(i));

            totalLabels.get(i).relocate(400, j + 5);
            totalLabels.get(i).setTextFill(Paint.valueOf("white"));
            totalLabels.get(i).setText("$" + MoneyTools.df.format(coinSlots.get(i).getTotal()));

            mainPane.getChildren().add(totalLabels.get(i));

            j += 40;
        }
        total.relocate(200, j + 30);
        total.setFont(Font.font(30));
        total.setTextFill(Paint.valueOf("white"));
        mainPane.getChildren().add(total);

        Scene scene = new Scene(mainPane, 500, numOfRows * 40 + 100);
        stage = new Stage();
        stage.setTitle("CoinSlotter - " + globalCountryString + " - " + globalCBBString);
        stage.setScene(scene);
        stage.show();
    }

}
