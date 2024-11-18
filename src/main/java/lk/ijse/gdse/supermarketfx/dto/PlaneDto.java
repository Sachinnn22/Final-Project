package lk.ijse.gdse.supermarketfx.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PlaneDto {
    private String planeId;
    private String planeName;
    private String flightClass;
    private String seatCount;
}
