package ee.kregor.decathlon.controller;

import ee.kregor.decathlon.dto.SportlaneDTO;
import ee.kregor.decathlon.dto.TulemusDTO;
import ee.kregor.decathlon.entity.Sportlane;
import ee.kregor.decathlon.entity.Tulemus;
import ee.kregor.decathlon.repository.SportlaneRepository;
import ee.kregor.decathlon.repository.TulemusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DecathlonController {

    @Autowired
    private SportlaneRepository sportlaneRepository;

    @Autowired
    private TulemusRepository tulemusRepository;


    @GetMapping("/sportlased")
    public Page<Sportlane> getSportlased(
            @RequestParam(defaultValue = "0")   int page,
            @RequestParam(defaultValue = "5")   int size,
            @RequestParam(defaultValue = "")    String riik,
            @RequestParam(defaultValue = "id,asc") String sort,
            @RequestParam(required = false)     String sortTulemus
    ) {

        if (sortTulemus != null && !sortTulemus.isBlank()) {
            String[] parts = sortTulemus.split(",");
            String spordiala = parts[0];
            boolean asc = parts.length < 2 || parts[1].equalsIgnoreCase("asc");
            Pageable pageable = PageRequest.of(page, size);
            if (asc) {
                return sportlaneRepository.findAllSortByTulemusAsc(riik, spordiala, pageable);
            } else {
                return sportlaneRepository.findAllSortByTulemusDesc(riik, spordiala, pageable);
            }
        }


        String[] sortParts = sort.split(",");
        String sortField = sortParts[0];
        Sort.Direction direction = sortParts.length > 1 && sortParts[1].equalsIgnoreCase("desc")
                ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
        return sportlaneRepository.findByRiikContainingIgnoreCase(riik, pageable);
    }



    @GetMapping("/sportlased/riigid")
    public List<String> getRiigid() {
        return sportlaneRepository.findAllRiigid();
    }


    @GetMapping("/sportlased/{id}")
    public Sportlane getSportlane(@PathVariable Long id) {
        return sportlaneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sportlast ID-ga " + id + " ei leitud"));
    }


    @PostMapping("/sportlased")
    @ResponseStatus(HttpStatus.CREATED)
    public Sportlane addSportlane(@RequestBody SportlaneDTO sportlaneDTO) {

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


    @DeleteMapping("/sportlased/{id}")
    public String deleteSportlane(@PathVariable Long id) {
        if (!sportlaneRepository.existsById(id)) {
            throw new RuntimeException("Sportlast ID-ga " + id + " ei leitud");
        }

        sportlaneRepository.deleteById(id);
        return "Sportlane kustutatud";
    }


    @PostMapping("/tulemused")
    @ResponseStatus(HttpStatus.CREATED)
    public Tulemus addTulemus(@RequestBody TulemusDTO tulemusDTO) {

        if (tulemusDTO.getSportlaneId() == null) {
            throw new RuntimeException("Sportlane ID on kohustuslik");
        }

        if (tulemusDTO.getSpordiala() == null || tulemusDTO.getSpordiala().trim().isEmpty()) {
            throw new RuntimeException("Spordiala on kohustuslik");
        }

        if (!tulemusDTO.getSpordiala().equals("100m") && !tulemusDTO.getSpordiala().equals("kaugushüpe")) {
            throw new RuntimeException("Spordiala peab olema kas '100m' või 'kaugushüpe'");
        }

        if (tulemusDTO.getTulemus() <= 0) {
            throw new RuntimeException("Tulemus peab olema positiivne arv");
        }

        Sportlane sportlane = sportlaneRepository.findById(tulemusDTO.getSportlaneId())
                .orElseThrow(() -> new RuntimeException("Sportlast ID-ga " + tulemusDTO.getSportlaneId() + " ei leitud"));

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


    @GetMapping("/sportlased/{id}/tulemused")
    public List<Tulemus> getSportlaseTulemused(@PathVariable Long id) {
        if (!sportlaneRepository.existsById(id)) {
            throw new RuntimeException("Sportlast ID-ga " + id + " ei leitud");
        }

        return tulemusRepository.findBySportlaneId(id);
    }



    private final RestTemplate restTemplate = new RestTemplate();
    private static final String MOCKAPI_KOHTUNIKUD_URL = "https://6a08ab0cfa9b27c848fb3abb.mockapi.io/api/v1/kohtunikud";
    private static final String MOCKAPI_ASUKOHAD_URL = "https://6a08ab0cfa9b27c848fb3abb.mockapi.io/api/v1/asukohad";

    @GetMapping("/kohtunikud")
    public Object getKohtunikud() {
        return restTemplate.getForObject(MOCKAPI_KOHTUNIKUD_URL, Object.class);
    }

    @GetMapping("/asukohad")
    public Object getAsukohad() {
        return restTemplate.getForObject(MOCKAPI_ASUKOHAD_URL, Object.class);
    }
}