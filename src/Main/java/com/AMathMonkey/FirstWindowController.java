package Main.java.com.AMathMonkey;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class FirstWindowController implements Initializable{

    @FXML
    private ComboBox<String> countryBox;
    @FXML
    private ComboBox<String> cbbBox;
    @FXML
    private AnchorPane anchor;

    private Map<String, MoneyTools.Country> countryMap = MoneyTools.countryMap();
    private Map<String, MoneyTools.CBB> cbbMap = MoneyTools.cbbMap();

    public FirstWindowController(){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        BackgroundSize bgs = new BackgroundSize(400, 300, false, false,
                false, false);
        anchor.setBackground(new Background(new BackgroundImage(new Image("Main/resources/Background.png"),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bgs)));

        countryBox.setItems(FXCollections.observableArrayList(countryMap.keySet()));
        cbbBox.setItems(FXCollections.observableArrayList(cbbMap.keySet()));
    }


    @FXML
    protected void handleDoneButtonAction() throws IOException {
        Object selectedCountry = countryBox.getSelectionModel().getSelectedItem();
        Object selectedCBB = cbbBox.getSelectionModel().getSelectedItem();
        if (selectedCountry != null && selectedCBB != null) {

            Stage stage = new Stage();

            MoneyTools.globalCountry = countryMap.get(selectedCountry.toString());
            MoneyTools.globalCBB = cbbMap.get(selectedCBB.toString());
            MoneyTools.globalCountryString = selectedCountry.toString();
            MoneyTools.globalCBBString = selectedCBB.toString();

            MainWindow mainWindow = new MainWindow();
            mainWindow.start(stage);
        }
    }
}
