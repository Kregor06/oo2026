package ee.kregor.autod.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutoDTO {
    private String mark;
    private String mudel;
    private int aasta;
    private String värv;
    private double hind;
}