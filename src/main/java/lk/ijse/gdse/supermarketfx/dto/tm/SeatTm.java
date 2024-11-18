package lk.ijse.gdse.supermarketfx.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString


public class SeatTm {
    private String seatId;
    private String planeId;
    private String seatClass;
    private String availability;
}
