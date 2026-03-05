package ee.kregor.decathlon.controller;

import ee.kregor.decathlon.dto.SportlaneDTO;
import ee.kregor.decathlon.dto.TulemusDTO;
import ee.kregor.decathlon.entity.Sportlane;
import ee.kregor.decathlon.entity.Tulemus;
import ee.kregor.decathlon.repository.SportlaneRepository;
import ee.kregor.decathlon.repository.TulemusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DecathlonController {

    @Autowired
    private SportlaneRepository sportlaneRepository;

    @Autowired
    private TulemusRepository tulemusRepository;

    // Kõik sportlased
    @GetMapping("/sportlased")
    public List<Sportlane> getSportlased() {
        return sportlaneRepository.findAll();
    }

    // Üks sportlane ID järgi
    @GetMapping("/sportlased/{id}")
    public Sportlane getSportlane(@PathVariable Long id) {
        return sportlaneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sportlast ID-ga " + id + " ei leitud"));
    }

    // Uue sportlase lisamine
    @PostMapping("/sportlased")
    @ResponseStatus(HttpStatus.CREATED)
    public Sportlane addSportlane(@RequestBody SportlaneDTO sportlaneDTO) {
        // Valideeri sisendid
        if (sportlaneDTO.getEesnimi() == null || sportlaneDTO.getEesnimi().trim().isEmpty()) {
            throw new RuntimeException("Eesnimi on kohustuslik");
        }

        if (sportlaneDTO.getPerenimi() == null || sportlaneDTO.getPerenimi().trim().isEmpty()) {
            throw new RuntimeException("Perenimi on kohustuslik");
        }

        if (sportlaneDTO.getSynniaasta() < 1900 || sportlaneDTO.getSynniaasta() > 2024) {
            throw new RuntimeException("Sünniaasta peab olema vahemikus 1900-2024");
        }

        Sportlane sportlane = new Sportlane();
        sportlane.setEesnimi(sportlaneDTO.getEesnimi());
        sportlane.setPerenimi(sportlaneDTO.getPerenimi());
        sportlane.setRiik(sportlaneDTO.getRiik());
        sportlane.setSynniaasta(sportlaneDTO.getSynniaasta());

        return sportlaneRepository.save(sportlane);
    }

    // Sportlase kustutamine
    @DeleteMapping("/sportlased/{id}")
    public String deleteSportlane(@PathVariable Long id) {
        if (!sportlaneRepository.existsById(id)) {
            throw new RuntimeException("Sportlast ID-ga " + id + " ei leitud");
        }

        sportlaneRepository.deleteById(id);
        return "Sportlane kustutatud";
    }

    // Tulemuse lisamine sportlasele
    @PostMapping("/tulemused")
    @ResponseStatus(HttpStatus.CREATED)
    public Tulemus addTulemus(@RequestBody TulemusDTO tulemusDTO) {
        // Valideeri sisendid
        if (tulemusDTO.getSportlaneId() == null) {
            throw new RuntimeException("Sportlane ID on kohustuslik");
        }

        if (tulemusDTO.getSpordiala() == null || tulemusDTO.getSpordiala().trim().isEmpty()) {
            throw new RuntimeException("Spordiala on kohustuslik");
        }

        // Kontrolli, kas spordiala on lubatud
        if (!tulemusDTO.getSpordiala().equals("100m") && !tulemusDTO.getSpordiala().equals("kaugushüpe")) {
            throw new RuntimeException("Spordiala peab olema kas '100m' või 'kaugushüpe'");
        }

        if (tulemusDTO.getTulemus() <= 0) {
            throw new RuntimeException("Tulemus peab olema positiivne arv");
        }

        // Leia sportlane
        Sportlane sportlane = sportlaneRepository.findById(tulemusDTO.getSportlaneId())
                .orElseThrow(() -> new RuntimeException("Sportlast ID-ga " + tulemusDTO.getSportlaneId() + " ei leitud"));

        // Kontrolli, kas sellel spordialal on juba tulemus olemas
        boolean onOlemas = tulemusRepository.existsBySportlaneIdAndSpordiala(
                tulemusDTO.getSportlaneId(),
                tulemusDTO.getSpordiala()
        );

        if (onOlemas) {
            throw new RuntimeException("Sellel sportlasel on juba tulemus spordialal " + tulemusDTO.getSpordiala());
        }

        Tulemus tulemus = new Tulemus();
        tulemus.setSpordiala(tulemusDTO.getSpordiala());
        tulemus.setTulemus(tulemusDTO.getTulemus());
        tulemus.setSportlane(sportlane);

        return tulemusRepository.save(tulemus);
    }

    // Sportlase kõik tulemused
    @GetMapping("/sportlased/{id}/tulemused")
    public List<Tulemus> getSportlaseTulemused(@PathVariable Long id) {
        if (!sportlaneRepository.existsById(id)) {
            throw new RuntimeException("Sportlast ID-ga " + id + " ei leitud");
        }

        return tulemusRepository.findBySportlaneId(id);
    }
}