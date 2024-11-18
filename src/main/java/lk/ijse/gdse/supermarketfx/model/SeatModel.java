package lk.ijse.gdse.supermarketfx.model;

import lk.ijse.gdse.supermarketfx.dto.SeatDto;
import lk.ijse.gdse.supermarketfx.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SeatModel {

    public String getNextSeatId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT seat_id FROM seats ORDER BY seat_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("S%03d", newIdIndex);
        }
        return "S001";
    }

    public boolean saveSeat(SeatDto seatDto) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO seats VALUES (?, ?, ?, ?)",
                seatDto.getSeatId(),
                seatDto.getPlaneId(),
                seatDto.getSeatClass(),
                seatDto.getAvailability()
        );
    }

    public ArrayList<SeatDto> getAllSeats() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM seats");

        ArrayList<SeatDto> seatDtos = new ArrayList<>();

        while (rst.next()) {
            SeatDto seatDto = new SeatDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            );
            seatDtos.add(seatDto);
        }
        return seatDtos;
    }

    public boolean updateSeat(SeatDto seatDto) throws SQLException {
        return CrudUtil.execute(
                "UPDATE seats SET plane_id = ?, seat_class = ?, availability = ? WHERE seat_id = ?",
                seatDto.getPlaneId(),
                seatDto.getSeatClass(),
                seatDto.getAvailability(),
                seatDto.getSeatId()
        );
    }

    public boolean deleteSeat(String seatId) throws SQLException {
        return CrudUtil.execute("DELETE FROM seats WHERE seat_id = ?", seatId);
    }

    public ArrayList<String> getAllSeatIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT seat_id FROM seats");

        ArrayList<String> seatIds = new ArrayList<>();

        while (rst.next()) {
            seatIds.add(rst.getString(1));
        }
        return seatIds;
    }

    public SeatDto findById(String selectedSeatId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM seats WHERE seat_id = ?", selectedSeatId);

        if (rst.next()) {
            return new SeatDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            );
        }
        return null;
    }

    public ArrayList<String> getSeatsByPlaneId(String planeId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT seat_id FROM seats WHERE plane_id = ?", planeId);

        ArrayList<String> seatIds = new ArrayList<>();

        while (rst.next()) {
            seatIds.add(rst.getString(1));
        }
        return seatIds;
    }
}
