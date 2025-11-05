package ee.wave.playsite.service;


import org.springframework.stereotype.Service;
import ee.wave.playsite.repository.PlaysitesRepository;
import ee.wave.playsite.repository.TicketsRepository;
import reactor.core.publisher.Mono;

@Service
public class PlaysitesService {

    private final PlaysitesRepository playsitesRepository;
    private final TicketsRepository ticketsRepository;

    public PlaysitesService(PlaysitesRepository playsitesRepository, TicketsRepository ticketsRepository) {
        this.playsitesRepository = playsitesRepository;
        this.ticketsRepository = ticketsRepository;
    }

    public Mono<Boolean> getMaxUsers(Integer playsiteId) {
        return Mono.just(true);
    }
}