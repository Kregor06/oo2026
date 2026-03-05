package ee.kregor.decathlon.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Sportlane {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eesnimi;
    private String perenimi;
    private String riik;
    private int synniaasta;

    @OneToMany(mappedBy = "sportlane", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"sportlane"})
    private List<Tulemus> tulemused = new ArrayList<>();
}