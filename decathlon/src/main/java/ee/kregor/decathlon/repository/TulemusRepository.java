package ee.kregor.decathlon.repository;

import ee.kregor.decathlon.entity.Tulemus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TulemusRepository extends JpaRepository<Tulemus, Long> {
    // Kõik sportlase tulemused
    List<Tulemus> findBySportlaneId(Long sportlaneId);

    // Kas sportlasel on juba tulemus sellel alal
    boolean existsBySportlaneIdAndSpordiala(Long sportlaneId, String spordiala);
}