package ee.wave.playsite.repository;

import org.springframework.stereotype.Repository;
import ee.wave.playsite.model.AttractionType; 
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class AttractionTypesRepository {

    private static final AttractionType[] attractionTypes = {
        new AttractionType(1, "Double swing", 2),
        new AttractionType(2, "Carousel,", 4),
        new AttractionType(3, "Ball pit", 20)
    };

    public Flux<AttractionType> getAll() {
        return Flux.fromArray(attractionTypes);
    }

    public Mono<AttractionType> findById(Integer id) {
        return Flux.fromArray(attractionTypes)
                .filter(type -> type.id().equals(id))
                .next(); 
    }
}