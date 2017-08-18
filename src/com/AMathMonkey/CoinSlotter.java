package com.AMathMonkey;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class CoinSlotter extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private boolean justOnce = false;
    @FXML private ComboBox countryBox = new ComboBox();
    @FXML private ComboBox currencyBox = new ComboBox();
    @FXML private AnchorPane anchor = new AnchorPane();
    private Stage stage = new Stage();


    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("CoinSlotter.fxml"));

        stage.setTitle("Welcome to CoinSlotter");

        stage.setScene(new Scene(root));
        stage.show();

    }

    @FXML protected void handleOpenAction(Event event) {
        if(!justOnce) {
            BackgroundSize bgs = new BackgroundSize(400,300,false, false, false, false);
            anchor.setBackground(new Background(new BackgroundImage(new Image("lol.png"),
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,bgs)));

            countryBox.setItems(FXCollections.observableArrayList(MoneyTools.Countries.values()));
            currencyBox.setItems(FXCollections.observableArrayList(MoneyTools.CoinsBillsOrBoth.values()));
            justOnce = true;
        }
    }

    @FXML protected void handleDoneButtonAction(ActionEvent event) throws IOException {
        if(countryBox.getSelectionModel().getSelectedItem() != null && currencyBox.getSelectionModel().getSelectedItem() != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CoinSlotterMainWindow.fxml"));
                Parent root1 = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                CoinSlotterMainWindow cmw = new CoinSlotterMainWindow();
                MoneyTools.globalCountry = (MoneyTools.Countries) countryBox.getSelectionModel().getSelectedItem();
                MoneyTools.globalCBB = (MoneyTools.CoinsBillsOrBoth) currencyBox.getSelectionModel().getSelectedItem();
                MoneyTools.globalStage = stage;
                cmw.start(stage);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
