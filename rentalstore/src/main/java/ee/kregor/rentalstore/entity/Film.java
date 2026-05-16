package ee.kregor.rentalstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private FilmType type; // Enum - kindlaksmääratud väärtuste hulgast saan valida
    private int days; // mitu päeva on renditud. 0 - available

    @ManyToOne
    private Rental rental;
}
