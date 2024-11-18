package lk.ijse.gdse.supermarketfx.model;

import lk.ijse.gdse.supermarketfx.dto.DestinationDTO;
import lk.ijse.gdse.supermarketfx.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DestinationModel {

    // Method to get the next available destination ID
    public String getNextDestinationId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT destination_id FROM destination ORDER BY destination_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String idNumberPart = lastId.substring(1); // Assuming IDs like "D001"
            int nextIdNumber = Integer.parseInt(idNumberPart) + 1;
            return String.format("D%03d", nextIdNumber); // Format as "D001", "D002", etc.
        }
        return "D001"; // Starting ID if no entries exist
    }

    // Method to save a new destination
    public boolean saveDestination(DestinationDTO destinationDTO) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO destination VALUES (?, ?, ?)",
                destinationDTO.getDestinationId(),
                destinationDTO.getDestinationName(),
                destinationDTO.getDistance()
        );
    }

    // Method to retrieve all destinations
    public ArrayList<DestinationDTO> getAllDestinations() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM destination");

        ArrayList<DestinationDTO> destinationDtos = new ArrayList<>();
        while (rst.next()) {
            DestinationDTO destinationDto = new DestinationDTO(
                    rst.getString("destination_id"),
                    rst.getString("destination_name"),
                    rst.getString("distance")
            );
            destinationDtos.add(destinationDto);
        }
        return destinationDtos;
    }

    // Method to update an existing destination
    public boolean updateDestination(DestinationDTO destinationDTO) throws SQLException {
        return CrudUtil.execute(
                "UPDATE destination SET destination_name=?, distance=? WHERE destination_id=?",
                destinationDTO.getDestinationName(),
                destinationDTO.getDistance(),
                destinationDTO.getDestinationId()
        );
    }

    // Method to delete a destination by ID
    public boolean deleteDestination(String destinationId) throws SQLException {
        return CrudUtil.execute("DELETE FROM destination WHERE destination_id=?", destinationId);
    }

    // Method to get a list of all destination IDs
    public ArrayList<String> getAllDestinationIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT destination_id FROM destination");

        ArrayList<String> destinationIds = new ArrayList<>();
        while (rst.next()) {
            destinationIds.add(rst.getString(1));
        }
        return destinationIds;
    }

    // Method to find a destination by ID
    public DestinationDTO findById(String selectedDestinationId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM destination WHERE destination_id=?", selectedDestinationId);

        if (rst.next()) {
            return new DestinationDTO(
                    rst.getString("destination_id"),
                    rst.getString("destination_name"),
                    rst.getString("distance")
            );
        }
        return null;
    }
}
