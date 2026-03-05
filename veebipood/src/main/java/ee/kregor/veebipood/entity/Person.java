package ee.kregor.veebipood.entity;

//import javax.persistence.*
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
public class Person { // User on hõivatud PostgreSQL tasandil
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true) // andmebaasis peab olema unikaalne
    private String email;
    private String password;
    @Column(unique = true)
    private String personalCode;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
}