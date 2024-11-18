package lk.ijse.gdse.supermarketfx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse.supermarketfx.dto.SeatDto;
import lk.ijse.gdse.supermarketfx.dto.tm.SeatTm;
import lk.ijse.gdse.supermarketfx.model.SeatModel;
import lk.ijse.gdse.supermarketfx.model.PlaneModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class SeatController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<SeatTm, String> colSeatId;

    @FXML
    private TableColumn<SeatTm, String> colPlaneId;

    @FXML
    private TableColumn<SeatTm, String> colSeatClass;

    @FXML
    private TableColumn<SeatTm, String> colAvibilty;

    @FXML
    private ComboBox<String> comboPlaneId;

    @FXML
    private ComboBox<String> comboSeatClass;

    @FXML
    private Label lblSeatId;

    @FXML
    private TableView<SeatTm> tblSeat;

    @FXML
    private TextField txtAvibility;

    private SeatModel seatModel = new SeatModel();
    private PlaneModel planeModel = new PlaneModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadComboBoxes();
            loadTableData();
            loadNextSeatId();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to initialize SeatController").show();
        }
    }

    private void loadComboBoxes() throws SQLException {
        // Load Plane IDs
        ArrayList<String> planeIds = planeModel.getAllPlaneIds();
        comboPlaneId.getItems().addAll(planeIds);

        // Load Seat Classes
        comboSeatClass.getItems().addAll("Economy", "Business", "First Class");
    }

    private void loadTableData() throws SQLException {
        ArrayList<SeatDto> seatDtos = seatModel.getAllSeats();
        ObservableList<SeatTm> seatTms = FXCollections.observableArrayList();

        for (SeatDto seatDto : seatDtos) {
            SeatTm seatTm = new SeatTm(
                    seatDto.getSeatId(),
                    seatDto.getPlaneId(),
                    seatDto.getSeatClass(),
                    seatDto.getAvailability()
            );
            seatTms.add(seatTm);
        }

        tblSeat.setItems(seatTms);
    }

    public void loadNextSeatId() throws SQLException {
        String nextSeatId = seatModel.getNextSeatId();
        lblSeatId.setText(nextSeatId);
    }

    @FXML
    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException {
        String seatId = lblSeatId.getText();
        String planeId = comboPlaneId.getValue();
        String seatClass = comboSeatClass.getValue();
        String availability = txtAvibility.getText();

        SeatDto seatDto = new SeatDto(
                seatId,
                planeId,
                seatClass,
                availability
        );

        boolean isSaved = seatModel.saveSeat(seatDto);
        if (isSaved) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Seat saved!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save seat!").show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String seatId = lblSeatId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {
            boolean isDeleted = seatModel.deleteSeat(seatId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Seat deleted!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete seat!").show();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String seatId = lblSeatId.getText();
        String planeId = comboPlaneId.getValue();
        String seatClass = comboSeatClass.getValue();
        String availability = txtAvibility.getText();

        SeatDto seatDto = new SeatDto(
                seatId,
                planeId,
                seatClass,
                availability
        );

        boolean isUpdated = seatModel.updateSeat(seatDto);
        if (isUpdated) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Seat updated!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to update seat!").show();
        }
    }

    @FXML
    void resetOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void onClickTable(MouseEvent event) {
        SeatTm seatTm = tblSeat.getSelectionModel().getSelectedItem();
        if (seatTm != null) {
            lblSeatId.setText(seatTm.getSeatId());
            comboPlaneId.setValue(seatTm.getPlaneId());
            comboSeatClass.setValue(seatTm.getSeatClass());
            txtAvibility.setText(seatTm.getAvailability());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    private void refreshPage() throws SQLException {
        loadNextSeatId();
        loadTableData();

        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        comboPlaneId.getSelectionModel().clearSelection();
        comboSeatClass.getSelectionModel().clearSelection();
        txtAvibility.clear();
    }

}
