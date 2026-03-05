package ee.kregor.decathlon.repository;

import ee.kregor.decathlon.entity.Sportlane;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SportlaneRepository extends JpaRepository<Sportlane, Long> {
    // Lihtsad päringud - Spring Data JPA teeb automaatselt
    List<Sportlane> findByPerenimi(String perenimi);
    List<Sportlane> findByEesnimiAndPerenimi(String eesnimi, String perenimi);
}