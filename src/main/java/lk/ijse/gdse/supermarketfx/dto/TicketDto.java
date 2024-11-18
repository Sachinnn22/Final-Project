package lk.ijse.gdse.supermarketfx.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class TicketDto {
    private String ticketId;
    private String destinationId;
    private String PlaneId;
    private String ticketClass;
    private Double ticketCost;
}
