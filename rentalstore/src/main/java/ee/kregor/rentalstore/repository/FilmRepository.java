package ee.kregor.rentalstore.repository;

import ee.kregor.rentalstore.entity.Film;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmRepository extends JpaRepository<@NonNull Film,@NonNull Long> {

    // SELECT * FROM film WHERE days =
    List<Film> findByDays(Integer days);
}
