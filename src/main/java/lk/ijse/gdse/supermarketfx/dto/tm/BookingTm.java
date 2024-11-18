package lk.ijse.gdse.supermarketfx.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class BookingTm {
    private String bookingId;
    private String bookingDate;
    private String userId;
    private String ticketId;
    private String destination;
}
