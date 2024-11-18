package lk.ijse.gdse.supermarketfx.model;

import lk.ijse.gdse.supermarketfx.dto.PlaneDto;
import lk.ijse.gdse.supermarketfx.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaneModel {

    public String getNextPlaneId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT plane_id FROM plane ORDER BY plane_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("P%03d", newIdIndex);
        }
        return "P001";
    }

    public boolean savePlane(PlaneDto planeDto) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO plane VALUES (?, ?, ?, ?)",
                planeDto.getPlaneId(),
                planeDto.getPlaneName(),
                planeDto.getFlightClass(),
                Integer.parseInt(planeDto.getSeatCount())
        );
    }

    public ArrayList<PlaneDto> getAllPlanes() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM plane");

        ArrayList<PlaneDto> planeDtos = new ArrayList<>();

        while (rst.next()) {
            PlaneDto planeDto = new PlaneDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    String.valueOf(rst.getInt(4))
            );
            planeDtos.add(planeDto);
        }
        return planeDtos;
    }

    public boolean updatePlane(PlaneDto planeDto) throws SQLException {
        return CrudUtil.execute(
                "UPDATE plane SET plane_name = ?, flight_class = ?, seat_count = ? WHERE plane_id = ?",
                planeDto.getPlaneName(),
                planeDto.getFlightClass(),
                Integer.parseInt(planeDto.getSeatCount()),
                planeDto.getPlaneId()
        );
    }

    public boolean deletePlane(String planeId) throws SQLException {
        return CrudUtil.execute("DELETE FROM plane WHERE plane_id = ?", planeId);
    }

    public ArrayList<String> getAllPlaneIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT plane_id FROM plane");

        ArrayList<String> planeIds = new ArrayList<>();

        while (rst.next()) {
            planeIds.add(rst.getString(1));
        }
        return planeIds;
    }

    public PlaneDto findById(String selectedPlaneId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM plane WHERE plane_id = ?", selectedPlaneId);

        if (rst.next()) {
            return new PlaneDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    String.valueOf(rst.getInt(4))
            );
        }
        return null;
    }
}
