package ee.wave.playsite.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ee.wave.playsite.model.Playsite;
import ee.wave.playsite.repository.PlaysitesRepository;
import ee.wave.playsite.request.CreatePlaysiteRequest;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/playsites")
public class PlaysitesController {

	private final PlaysitesRepository playsitesRepository;

	public PlaysitesController(PlaysitesRepository playsitesRepository) {
		this.playsitesRepository = playsitesRepository;
	}

	@GetMapping("/{id}")
	public Mono<Playsite> findById(@PathVariable Integer id) {
		return this.playsitesRepository.findById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Playsite> createOne(@RequestBody CreatePlaysiteRequest request) {
		return this.playsitesRepository.createOne(request);
	}
}
