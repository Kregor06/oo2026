package ee.kregor.decathlon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SportlaneDTO {
    private String eesnimi;
    private String perenimi;
    private String riik;
    private int synniaasta;
}