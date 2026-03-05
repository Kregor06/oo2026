package ee.kregor.decathlon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TulemusDTO {
    private Long sportlaneId;
    private String spordiala;
    private double tulemus;
}