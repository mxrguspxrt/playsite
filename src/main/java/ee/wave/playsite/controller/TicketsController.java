package ee.wave.playsite.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ee.wave.playsite.model.Ticket;
import ee.wave.playsite.service.TicketsService;
import ee.wave.playsite.request.CreateTicketRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/playsites/{playsiteId}/tickets")
public class TicketsController {

    private final TicketsService ticketsService;

    public TicketsController(TicketsService ticketsService) {
        this.ticketsService = ticketsService;
    }

    @GetMapping
    public Flux<Ticket> getAllTicketsForPlaysite(@PathVariable Integer playsiteId) {
        return this.ticketsService.getAllForPlaysite(playsiteId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Ticket> createOne(@PathVariable Integer playsiteId, @RequestBody CreateTicketRequest request) {
        return this.ticketsService.createOne(playsiteId, request);
    }
}