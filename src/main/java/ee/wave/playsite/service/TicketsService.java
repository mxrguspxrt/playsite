package ee.wave.playsite.service;

import org.springframework.stereotype.Service;

import ee.wave.playsite.model.Ticket;
import ee.wave.playsite.repository.TicketsRepository;
import ee.wave.playsite.request.CreateTicketRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TicketsService {
    private final TicketsRepository ticketsRepository;
    private final PlaysiteService playsiteService;

    public TicketsService(TicketsRepository ticketsRepository, PlaysiteService playsiteService) {
        this.ticketsRepository = ticketsRepository;
        this.playsiteService = playsiteService;
    }

    public Mono<Ticket> findById(Integer id) {
        return ticketsRepository.findById(id);
    }

    public Flux<Ticket> getAllForPlaysite(Integer playsiteId) {
        return this.ticketsRepository.getAllForPlaysite(playsiteId);
    }

    public Mono<Ticket> createOne(Integer playsiteId, CreateTicketRequest request) {
        return this.playsiteService.hasFreeRoom(playsiteId).flatMap(hasFreeRoom -> {
            if (hasFreeRoom) {
                return joinPlaysite(playsiteId, request, false);
            } else {
                if (request.acceptWaitingInQueue()) {
                    return this.joinPlaysite(playsiteId, request, true);
                } else {
                    return Mono.empty();
                }
            }
        });
    }

    public Mono<Ticket> joinPlaysite(Integer playsiteId, CreateTicketRequest request, Boolean inQueue) {
        return this.ticketsRepository.createOne(request.userId(), playsiteId, inQueue);
    }
}
