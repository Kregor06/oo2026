package ee.kregor.filmikogu.controller;


import ee.kregor.filmikogu.entity.Film;
import ee.kregor.filmikogu.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FilmController {

    @Autowired
    private FilmRepository FilmRepository;

    @GetMapping("films")
    public List<Film> getFilms(){
        return FilmRepository.findAll();
    }

    @DeleteMapping("films/{id}")
    public List<Film> deleteFilm(@PathVariable Long id){
        FilmRepository.deleteById(id);
        return FilmRepository.findAll();
    }

    @PostMapping("films")
    public List<Film> addFilm(@RequestBody Film film){
        FilmRepository.save(film);
        return FilmRepository.findAll();
    }

}
