package ee.kregor.autod.controller;

import ee.kregor.autod.dto.AutoDTO;
import ee.kregor.autod.entity.Auto;
import ee.kregor.autod.repository.AutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/autod")
public class AutoController {

    @Autowired
    private AutoRepository autoRepository;


    @GetMapping
    public List<Auto> getAutod() {
        return autoRepository.findAll();
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Auto addAuto(@RequestBody AutoDTO autoDTO) {


        if (autoDTO.getMark() == null || autoDTO.getMark().trim().isEmpty()) {
            throw new RuntimeException("Auto mark on kohustuslik");
        }


        if (autoDTO.getMudel() == null || autoDTO.getMudel().trim().isEmpty()) {
            throw new RuntimeException("Auto mudel on kohustuslik");
        }


        if (autoDTO.getAasta() < 1900) {
            throw new RuntimeException("Auto aasta peab olema vähemalt 1900");
        }


        if (autoDTO.getAasta() > 2026) {
            throw new RuntimeException("Auto aasta ei saa olla tulevikus");
        }


        if (autoDTO.getHind() < 0) {
            throw new RuntimeException("Auto hind ei saa olla negatiivne");
        }


        Auto auto = new Auto();
        auto.setMark(autoDTO.getMark());
        auto.setMudel(autoDTO.getMudel());
        auto.setAasta(autoDTO.getAasta());
        auto.setVärv(autoDTO.getVärv());
        auto.setHind(autoDTO.getHind());

        return autoRepository.save(auto);
    }
}