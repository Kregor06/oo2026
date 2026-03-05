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

    // Kõik autod
    @GetMapping
    public List<Auto> getAutod() {
        return autoRepository.findAll();
    }

    // Uue auto lisamine - 5 erinevat veateadet
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Auto addAuto(@RequestBody AutoDTO autoDTO) {

        // Veateade 1: Mark on tühi
        if (autoDTO.getMark() == null || autoDTO.getMark().trim().isEmpty()) {
            throw new RuntimeException("Auto mark on kohustuslik");
        }

        // Veateade 2: Mudel on tühi
        if (autoDTO.getMudel() == null || autoDTO.getMudel().trim().isEmpty()) {
            throw new RuntimeException("Auto mudel on kohustuslik");
        }

        // Veateade 3: Aasta on vale (liiga vana)
        if (autoDTO.getAasta() < 1900) {
            throw new RuntimeException("Auto aasta peab olema vähemalt 1900");
        }

        // Veateade 4: Aasta on vale (tulevikus)
        if (autoDTO.getAasta() > 2026) { // 2026 on praegune aasta + 1
            throw new RuntimeException("Auto aasta ei saa olla tulevikus");
        }

        // Veateade 5: Hind on negatiivne
        if (autoDTO.getHind() < 0) {
            throw new RuntimeException("Auto hind ei saa olla negatiivne");
        }

        // Kõik OK - salvesta auto
        Auto auto = new Auto();
        auto.setMark(autoDTO.getMark());
        auto.setMudel(autoDTO.getMudel());
        auto.setAasta(autoDTO.getAasta());
        auto.setVärv(autoDTO.getVärv());
        auto.setHind(autoDTO.getHind());

        return autoRepository.save(auto);
    }
}