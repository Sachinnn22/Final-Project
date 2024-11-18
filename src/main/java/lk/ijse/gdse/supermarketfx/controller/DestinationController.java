package lk.ijse.gdse.supermarketfx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse.supermarketfx.dto.DestinationDTO;
import lk.ijse.gdse.supermarketfx.model.DestinationModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class DestinationController implements Initializable {

    @FXML
    private Button btnDeleteItem;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSaveItem;

    @FXML
    private Button btnUpdateItem;

    @FXML
    private TableColumn<DestinationDTO, String> colDestinationId;

    @FXML
    private TableColumn<DestinationDTO, String> colName;

    @FXML
    private TableColumn<DestinationDTO, String> colDistance;

    @FXML
    private Label lblDestinationId;

    @FXML
    private TableView<DestinationDTO> tblDestination;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtDistance;

    private final DestinationModel destinationModel = new DestinationModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set cell value factories for the table columns
        colDestinationId.setCellValueFactory(new PropertyValueFactory<>("destinationId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("destinationName"));
        colDistance.setCellValueFactory(new PropertyValueFactory<>("distance"));

        // Load data and setup UI state
        try {
            refreshPage();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load destinations").show();
        }
    }

    private void refreshPage() throws SQLException {
        loadNextDestinationId();
        loadTableData();

        btnSaveItem.setDisable(false);
        btnUpdateItem.setDisable(true);
        btnDeleteItem.setDisable(true);

        txtName.clear();
        txtDistance.clear();
    }

    private void loadTableData() throws SQLException {
        ArrayList<DestinationDTO> destinationDTOS = destinationModel.getAllDestinations();
        ObservableList<DestinationDTO> destinationList = FXCollections.observableArrayList(destinationDTOS);
        tblDestination.setItems(destinationList);
    }

    private void loadNextDestinationId() throws SQLException {
        String nextDestinationId = destinationModel.getNextDestinationId();
        lblDestinationId.setText(nextDestinationId);
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String destinationId = lblDestinationId.getText();
        String name = txtName.getText();
        String distance = txtDistance.getText();

        DestinationDTO destinationDTO = new DestinationDTO(destinationId, name, distance);
        boolean isSaved = destinationModel.saveDestination(destinationDTO);

        if (isSaved) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Destination saved!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save destination!").show();
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        DestinationDTO selectedDestination = tblDestination.getSelectionModel().getSelectedItem();
        if (selectedDestination != null) {
            lblDestinationId.setText(selectedDestination.getDestinationId());
            txtName.setText(selectedDestination.getDestinationName());
            txtDistance.setText(selectedDestination.getDistance());

            btnSaveItem.setDisable(true);
            btnUpdateItem.setDisable(false);
            btnDeleteItem.setDisable(false);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String destinationId = lblDestinationId.getText();
        String name = txtName.getText();
        String distance = txtDistance.getText();

        DestinationDTO destinationDTO = new DestinationDTO(destinationId, name, distance);
        boolean isUpdated = destinationModel.updateDestination(destinationDTO);

        if (isUpdated) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Destination updated!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to update destination!").show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String destinationId = lblDestinationId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            boolean isDeleted = destinationModel.deleteDestination(destinationId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Destination deleted!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete destination!").show();
            }
        }
    }

    @FXML
    void resetOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }
}
