package ee.kregor.kontrolltoo1.repository;

import ee.kregor.kontrolltoo1.entity.Sona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SonaRepository extends JpaRepository<Sona,Long> {

}

