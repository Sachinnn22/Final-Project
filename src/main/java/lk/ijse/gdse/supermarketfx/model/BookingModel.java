package lk.ijse.gdse.supermarketfx.model;

import lk.ijse.gdse.supermarketfx.dto.BookingDto;
import lk.ijse.gdse.supermarketfx.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookingModel {

    // Method to generate the next booking ID
    public String getNextBookingId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT booking_id FROM booking ORDER BY booking_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("B%03d", newIdIndex);
        }
        return "B001";
    }

    // Method to place a booking
    public boolean placeBooking(BookingDto bookingDto) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO booking (booking_id, nic, mobile_number, ticket_id, destination, ticket_price, pay_price, balance, user_name) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                bookingDto.getBookingId(),
                bookingDto.getNic(),
                bookingDto.getMobileNumber(),
                bookingDto.getTicketId(),
                bookingDto.getDestination(),
                bookingDto.getTicketPrice(),
                bookingDto.getPayPrice(),
                bookingDto.getBalance(),
                bookingDto.getUserName()
        );
    }

    // Method to retrieve all bookings
    public ArrayList<BookingDto> getAllBookings() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM booking");

        ArrayList<BookingDto> bookingList = new ArrayList<>();
        while (rst.next()) {
            BookingDto bookingDto = new BookingDto();
            bookingDto.setBookingId(rst.getString("booking_id"));
            bookingDto.setNic(rst.getString("nic"));
            bookingDto.setMobileNumber(rst.getString("mobile_number"));
            bookingDto.setTicketId(rst.getString("ticket_id"));
            bookingDto.setDestination(rst.getString("destination"));
            bookingDto.setTicketPrice(rst.getDouble("ticket_price"));
            bookingDto.setPayPrice(rst.getDouble("pay_price"));
            bookingDto.setBalance(rst.getDouble("balance"));
            bookingDto.setUserName(rst.getString("user_name"));
            bookingList.add(bookingDto);
        }
        return bookingList;
    }

    // Method to find a booking by ID
    public BookingDto findBookingById(String bookingId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM booking WHERE booking_id=?", bookingId);

        if (rst.next()) {
            BookingDto bookingDto = new BookingDto();
            bookingDto.setBookingId(rst.getString("booking_id"));
            bookingDto.setNic(rst.getString("nic"));
            bookingDto.setMobileNumber(rst.getString("mobile_number"));
            bookingDto.setTicketId(rst.getString("ticket_id"));
            bookingDto.setDestination(rst.getString("destination"));
            bookingDto.setTicketPrice(rst.getDouble("ticket_price"));
            bookingDto.setPayPrice(rst.getDouble("pay_price"));
            bookingDto.setBalance(rst.getDouble("balance"));
            bookingDto.setUserName(rst.getString("user_name"));
            return bookingDto;
        }
        return null;
    }

    // Method to delete a booking
    public boolean deleteBooking(String bookingId) throws SQLException {
        return CrudUtil.execute("DELETE FROM booking WHERE booking_id=?", bookingId);
    }
}
