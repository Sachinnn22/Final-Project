package lk.ijse.gdse.supermarketfx.controller;

/**
 * --------------------------------------------
 * Author: R.I.B. Shamodha Sahan Rathnamalala
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.live
 * --------------------------------------------
 * Created: 10/4/2024 9:18 AM
 * Project: supermarketfx
 * --------------------------------------------
 **/

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainLayoutController implements Initializable {

    @FXML
    private AnchorPane content;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigateTo("/view/PlaneView.fxml");
    }

    @FXML
    void navigateToPlanePage(ActionEvent event) {
        navigateTo("/view/PlaneView.fxml");
    }

    @FXML
    void navigateToDestinationPage(ActionEvent event) {
        navigateTo("/view/DestinationView.fxml");
    }

    @FXML
    void navigateToTicketPage(ActionEvent event) {
        navigateTo("/view/TicketView.fxml");
    }

    public void navigateTo(String fxmlPath) {
        try {
            content.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }
    }

}
