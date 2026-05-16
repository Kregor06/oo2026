package ee.kregor.decathlon.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Tulemus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String spordiala;
    private double tulemus;

    @ManyToOne
    @JoinColumn(name = "sportlane_id")
    @JsonIgnoreProperties({"tulemused"})
    private Sportlane sportlane;
}