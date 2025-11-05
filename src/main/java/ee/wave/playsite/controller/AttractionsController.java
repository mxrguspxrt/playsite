package ee.wave.playsite.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ee.wave.playsite.model.Attraction;
import ee.wave.playsite.repository.AttractionsRepository;
import ee.wave.playsite.request.CreateAttractionRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/playsites/{playsiteId}/attractions")
public class AttractionsController {

    private final AttractionsRepository attractionsRepository;

    public AttractionsController(AttractionsRepository attractionsRepository) {
        this.attractionsRepository = attractionsRepository;
    }

    @GetMapping
    public Flux<Attraction> getAllAttractionsForPlaysite(@PathVariable Integer playsiteId) {
        return this.attractionsRepository.findAllForPlaysite(playsiteId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Attraction> createOne(@PathVariable Integer playsiteId, @RequestBody CreateAttractionRequest request) {
        return this.attractionsRepository.createOne(playsiteId, request);
    }
}
