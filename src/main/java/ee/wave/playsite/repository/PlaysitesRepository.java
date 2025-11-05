package ee.wave.playsite.repository;

import org.springframework.stereotype.Repository;
import ee.wave.playsite.model.Playsite;
import ee.wave.playsite.request.CreatePlaySiteRequest;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class PlaysitesRepository {

    private static final Map<Integer, Playsite> playsites = new ConcurrentHashMap<>();
    private static final AtomicInteger idGenerator = new AtomicInteger();

    public Mono<Playsite> findById(Integer playsiteId) {
        return Mono.justOrEmpty(playsites.get(playsiteId));
    }

    public Mono<Playsite> createOne(CreatePlaySiteRequest request) {
        Playsite newPlaysite = new Playsite(idGenerator.incrementAndGet(), request.name());
        playsites.put(newPlaysite.id(), newPlaysite);
        
        return Mono.just(newPlaysite);
    }
}