package lk.ijse.gdse.supermarketfx.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class TicketTm {
    private String ticketId;
    private String destinationId;
    private String PlaneId;
    private String ticketClass;
    private Double ticketCost;

}
