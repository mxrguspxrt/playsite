package ee.wave.playsite.repository;

import org.springframework.stereotype.Repository;

import ee.wave.playsite.model.Attraction;
import ee.wave.playsite.model.AttractionType;
import ee.wave.playsite.request.CreateAttractionRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AttractionsRepository {

    private static final Map<Integer, Attraction> attractions = new ConcurrentHashMap<>();
    private static final AtomicInteger idGenerator = new AtomicInteger();

    private final AttractionTypesRepository attractionTypesRepository;

    public AttractionsRepository(AttractionTypesRepository attractionTypesRepository) {
        this.attractionTypesRepository = attractionTypesRepository;
    }

    public Mono<Attraction> findById(Integer id) {
        return Mono.justOrEmpty(attractions.get(id));
    }

    public Mono<Attraction> createOne(Integer playsiteId, CreateAttractionRequest request) {
        Attraction attraction = new Attraction(idGenerator.incrementAndGet(), request.attractionTypeId(), playsiteId);
        attractions.put(attraction.id(), attraction);

        return Mono.just(attraction);
    }

    public Flux<Attraction> findAllForPlaysite(Integer playsiteId) {
        return Flux.fromIterable(attractions.values())
                .filter(attraction -> attraction.playsiteId().equals(playsiteId));
    }

    public Mono<Integer> getMaxAllowed(Integer playsiteId) {
        return this.findAllForPlaysite(playsiteId)
            .map(Attraction::attractionTypeId)
            .flatMap(this.attractionTypesRepository::findById)
            .map(AttractionType::max_users)
            .reduce(0, Integer::sum);
    }
}