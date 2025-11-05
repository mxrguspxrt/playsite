package ee.wave.playsite.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import ee.wave.playsite.model.AttractionType;
import ee.wave.playsite.repository.AttractionTypesRepository;

@RestController
@RequestMapping("/attraction-types")
public class AttractionTypesController {

    private final AttractionTypesRepository attractionTypesRepository;

    public AttractionTypesController(AttractionTypesRepository attractionTypesRepository) {
        this.attractionTypesRepository = attractionTypesRepository;
    }

    @GetMapping
    public Flux<AttractionType> getAll() {
        return this.attractionTypesRepository.getAll();
    }
}