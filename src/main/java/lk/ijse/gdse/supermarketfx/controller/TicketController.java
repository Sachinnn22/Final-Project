package lk.ijse.gdse.supermarketfx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse.supermarketfx.dto.TicketDto;
import lk.ijse.gdse.supermarketfx.dto.tm.TicketTm;
import lk.ijse.gdse.supermarketfx.model.DestinationModel;
import lk.ijse.gdse.supermarketfx.model.PlaneModel;
import lk.ijse.gdse.supermarketfx.model.TicketModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class TicketController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<String> cmbDestinationId;

    @FXML
    private ComboBox<String> cmbPlaneId;

    @FXML
    private ComboBox<String> cmbClass;

    @FXML
    private TableColumn<TicketTm, String> colDestinationId;

    @FXML
    private TableColumn<TicketTm, String> colPlaneId;

    @FXML
    private TableColumn<TicketTm, String> colTicketClass;

    @FXML
    private TableColumn<TicketTm, Double> colTicketCost;

    @FXML
    private TableColumn<TicketTm, String> colTicketId;

    @FXML
    private Label lblTicketId;

    @FXML
    private TableView<TicketTm> tblTickets;

    @FXML
    private TextField txtTicketCost;

    private TicketModel ticketModel = new TicketModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colTicketId.setCellValueFactory(new PropertyValueFactory<>("ticketId"));
        colDestinationId.setCellValueFactory(new PropertyValueFactory<>("destinationId"));
        colPlaneId.setCellValueFactory(new PropertyValueFactory<>("planeId"));
        colTicketClass.setCellValueFactory(new PropertyValueFactory<>("ticketClass"));
        colTicketCost.setCellValueFactory(new PropertyValueFactory<>("ticketCost"));

        try {
            btnResetOnAction();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load data").show();
        }
    }

    private void btnResetOnAction() throws SQLException {
        loadNextTicketId();
        loadTableData();
        loadComboBoxes();

        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        cmbDestinationId.getSelectionModel().clearSelection();
        cmbPlaneId.getSelectionModel().clearSelection();
        cmbClass.getSelectionModel().clearSelection();
        txtTicketCost.clear();
    }

    private void loadComboBoxes() throws SQLException {
        ArrayList<String> destinationIds = new DestinationModel().getAllDestinationIds();
        cmbDestinationId.setItems(FXCollections.observableArrayList(destinationIds));

        ArrayList<String> planeIds = new PlaneModel().getAllPlaneIds();
        cmbPlaneId.setItems(FXCollections.observableArrayList(planeIds));

        cmbClass.setItems(FXCollections.observableArrayList("Business", "First Class", "Second"));
    }

    private void loadTableData() throws SQLException {
        ArrayList<TicketDto> tickets = ticketModel.getAllTickets();
        ObservableList<TicketTm> ticketTms = FXCollections.observableArrayList();

        for (TicketDto ticket : tickets) {
            TicketTm ticketTM = new TicketTm(
                    ticket.getTicketId(),
                    ticket.getDestinationId(),
                    ticket.getPlaneId(),
                    ticket.getTicketClass(),
                    ticket.getTicketCost()
            );
            ticketTms.add(ticketTM);
        }

        tblTickets.setItems(ticketTms);
    }

    private void loadNextTicketId() throws SQLException {
        String nextTicketId = ticketModel.getNextTicketId();
        lblTicketId.setText(nextTicketId);
    }

    @FXML
    void btnSaveOnAcction(ActionEvent event) throws SQLException {
        TicketDto ticket = new TicketDto(
                lblTicketId.getText(),
                cmbDestinationId.getValue(),
                cmbPlaneId.getValue(),
                cmbClass.getValue(),
                Double.parseDouble(txtTicketCost.getText())
        );

        boolean isSaved = ticketModel.saveTicket(ticket);
        if (isSaved) {
            btnResetOnAction();
            new Alert(Alert.AlertType.INFORMATION, "Ticket saved!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save ticket!").show();
        }
    }

    @FXML
    void btnUpdateOnAcction(ActionEvent event) throws SQLException {
        TicketDto ticket = new TicketDto(
                lblTicketId.getText(),
                cmbDestinationId.getValue(),
                cmbPlaneId.getValue(),
                cmbClass.getValue(),
                Double.parseDouble(txtTicketCost.getText())
        );

        boolean isUpdated = ticketModel.updateTicket(ticket);
        if (isUpdated) {
            btnResetOnAction();
            new Alert(Alert.AlertType.INFORMATION, "Ticket updated!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to update ticket!").show();
        }
    }

    @FXML
    void btnDeleteOnAcction(ActionEvent event) throws SQLException {
        String ticketId = lblTicketId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {
            boolean isDeleted = ticketModel.deleteTicket(ticketId);
            if (isDeleted) {
                btnResetOnAction();
                new Alert(Alert.AlertType.INFORMATION, "Ticket deleted!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete ticket!").show();
            }
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        TicketTm selectedTicket = tblTickets.getSelectionModel().getSelectedItem();
        if (selectedTicket != null) {
            lblTicketId.setText(selectedTicket.getTicketId());
            cmbDestinationId.setValue(selectedTicket.getDestinationId());
            cmbPlaneId.setValue(selectedTicket.getPlaneId());
            cmbClass.setValue(selectedTicket.getTicketClass());
            txtTicketCost.setText(String.valueOf(selectedTicket.getTicketCost()));

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}
