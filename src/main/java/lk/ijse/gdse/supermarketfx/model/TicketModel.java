package lk.ijse.gdse.supermarketfx.model;

import lk.ijse.gdse.supermarketfx.dto.TicketDto;
import lk.ijse.gdse.supermarketfx.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TicketModel {

    public String getNextTicketId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT ticket_id FROM ticket ORDER BY ticket_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("T%03d", newIdIndex);
        }
        return "T001";
    }

    public boolean saveTicket(TicketDto ticketDto) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO ticket VALUES (?, ?, ?, ?, ?, ?)",
                ticketDto.getTicketId(),
                ticketDto.getDestinationId(),
                ticketDto.getPlaneId(),
                ticketDto.getTicketClass(),
                ticketDto.getTicketCost()
        );
    }

    public ArrayList<TicketDto> getAllTickets() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM ticket");

        ArrayList<TicketDto> ticketDtos = new ArrayList<>();

        while (rst.next()) {
            TicketDto ticketDto = new TicketDto(
                    rst.getString(1),  // ticket_id
                    rst.getString(2),  // destination_id
                    rst.getString(3),  // plane_id
                    rst.getString(4),  // seat_class
                    rst.getDouble(5)   // ticket_cost
            );
            ticketDtos.add(ticketDto);
        }
        return ticketDtos;
    }

    public boolean updateTicket(TicketDto ticketDto) throws SQLException {
        return CrudUtil.execute(
                "UPDATE ticket SET destination_id = ?, plane_id = ?, seat_class = ?, ticket_class = ?, ticket_cost = ? WHERE ticket_id = ?",
                ticketDto.getDestinationId(),
                ticketDto.getPlaneId(),
                ticketDto.getTicketClass(),
                ticketDto.getTicketCost(),
                ticketDto.getTicketId()
        );
    }

    public boolean deleteTicket(String ticketId) throws SQLException {
        return CrudUtil.execute("DELETE FROM ticket WHERE ticket_id = ?", ticketId);
    }

    public ArrayList<String> getAllTicketIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT ticket_id FROM ticket");

        ArrayList<String> ticketIds = new ArrayList<>();

        while (rst.next()) {
            ticketIds.add(rst.getString(1));
        }
        return ticketIds;
    }

    public TicketDto findById(String selectedTicketId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM ticket WHERE ticket_id = ?", selectedTicketId);

        if (rst.next()) {
            return new TicketDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5)
            );
        }
        return null;
    }
}
