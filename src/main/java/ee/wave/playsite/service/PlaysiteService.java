package ee.wave.playsite.service;

import org.springframework.stereotype.Service;
import ee.wave.playsite.repository.TicketsRepository;
import ee.wave.playsite.repository.AttractionsRepository;
import reactor.core.publisher.Mono;

@Service
public class PlaysiteService {
    private final TicketsRepository ticketsRepository;
    private final AttractionsRepository attractionsRepository;

    public PlaysiteService(TicketsRepository ticketsRepository, AttractionsRepository attractionsRepository) {
        this.ticketsRepository = ticketsRepository;
        this.attractionsRepository = attractionsRepository;
    }

    public Mono<Boolean> hasFreeRoom(Integer playsiteId) {
        Mono<Integer> maxAllowedMono = this.attractionsRepository.getMaxAllowedCount(playsiteId);
        Mono<Long> activeUsersCountMono = this.ticketsRepository.getActiveForPlaysiteCount(playsiteId);

        return Mono.zip(maxAllowedMono, activeUsersCountMono)
                .map(tuple -> {
                    Long maxAllowed = (long) tuple.getT1();
                    Long activeUsersCount = tuple.getT2();

                    return activeUsersCount < maxAllowed;
                });
    }
}
