package ee.kregor.filmikogu.repository;

import ee.kregor.filmikogu.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FilmRepository extends JpaRepository<Film, Long> {
}