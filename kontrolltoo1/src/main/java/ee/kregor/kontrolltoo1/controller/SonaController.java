package ee.kregor.kontrolltoo1.controller;

import ee.kregor.kontrolltoo1.dto.SonaDTO;
import ee.kregor.kontrolltoo1.entity.Sona;
import ee.kregor.kontrolltoo1.repository.SonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SonaController {

    @Autowired
    private SonaRepository sonaRepository;

    @GetMapping("sonad")
    public List<Sona> getSonad(){
        return sonaRepository.findAll();
    }

    @PostMapping("sonad")
    public List<Sona> addSona(@RequestBody Sona sona) {

        if (sona.getTekst() == null) {
            throw new RuntimeException("Sõna ei ole sisestatud");
        }

        String tekst = sona.getTekst();
        for (int i = 0; i < tekst.length(); i++) {
            if (!Character.isLetter(tekst.charAt(i))) {
                throw new RuntimeException("Sõna tohib sisaldada ainult tähti");
            }
        }

        sonaRepository.save(sona);
        return sonaRepository.findAll();
    }

    @GetMapping("sonad/loputahed")
    public List<String> getLoputahed(){
        List<Sona> sonad = sonaRepository.findAll();
        List<String> loputahed = new ArrayList<>();

        for (Sona sona : sonad) {
            String tekst = sona.getTekst();
            loputahed.add(tekst.substring(tekst.length() - 1));
        }

        return loputahed;
    }

    @GetMapping("sonad/pikkused")
    public List<Integer> getPikkused(){
        List<Sona> sonad = sonaRepository.findAll();
        List<Integer> pikkused = new ArrayList<>();

        for (Sona sona : sonad) {
            pikkused.add(sona.getTekst().length());
        }

        return pikkused;
    }

    @GetMapping("sonad/tagurpidi")
    public List<String> getTagurpidi(){
        List<Sona> sonad = sonaRepository.findAll();
        List<String> tagurpidiSonad = new ArrayList<>();

        for (Sona sona : sonad) {
            String tagurpidi = new StringBuilder(sona.getTekst()).reverse().toString();
            tagurpidiSonad.add(tagurpidi);
        }

        return tagurpidiSonad;
    }
}
