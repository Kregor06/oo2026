package ee.kregor.decathlon.repository;

import ee.kregor.decathlon.entity.Sportlane;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SportlaneRepository extends JpaRepository<Sportlane, Long> {


    List<Sportlane> findByPerenimi(String perenimi);
    List<Sportlane> findByEesnimiAndPerenimi(String eesnimi, String perenimi);


    Page<Sportlane> findByRiikContainingIgnoreCase(String riik, Pageable pageable);


    @Query("SELECT DISTINCT s.riik FROM Sportlane s WHERE s.riik IS NOT NULL AND s.riik <> '' ORDER BY s.riik")
    List<String> findAllRiigid();


    @Query("""
        SELECT s FROM Sportlane s
        LEFT JOIN s.tulemused t ON t.spordiala = :spordiala
        WHERE (:riik = '' OR LOWER(s.riik) LIKE LOWER(CONCAT('%', :riik, '%')))
        ORDER BY t.tulemus ASC NULLS LAST
        """)
    Page<Sportlane> findAllSortByTulemusAsc(@Param("riik") String riik,
                                            @Param("spordiala") String spordiala,
                                            Pageable pageable);

    @Query("""
        SELECT s FROM Sportlane s
        LEFT JOIN s.tulemused t ON t.spordiala = :spordiala
        WHERE (:riik = '' OR LOWER(s.riik) LIKE LOWER(CONCAT('%', :riik, '%')))
        ORDER BY t.tulemus DESC NULLS LAST
        """)
    Page<Sportlane> findAllSortByTulemusDesc(@Param("riik") String riik,
                                             @Param("spordiala") String spordiala,
                                             Pageable pageable);
}