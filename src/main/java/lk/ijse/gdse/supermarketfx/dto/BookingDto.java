package lk.ijse.gdse.supermarketfx.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class BookingDto {
    private String bookingId;
    private String nic;
    private String mobileNumber;
    private String ticketId;
    private String destination;
    private double ticketPrice;
    private double payPrice;
    private double balance;
    private String userName;
}
