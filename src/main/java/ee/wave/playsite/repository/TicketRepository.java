package ee.wave.playsite.repository;

import org.springframework.stereotype.Repository;
import ee.wave.playsite.model.Ticket;
import ee.wave.playsite.request.CreateTicketRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class TicketRepository {

    private static final Map<Integer, Ticket> tickets = new ConcurrentHashMap<>();
    private static final AtomicInteger idGenerator = new AtomicInteger();

    public Mono<Ticket> findById(Integer id) {
        return Mono.justOrEmpty(tickets.get(id));
    }

    public Mono<Ticket> createOne(Integer playsiteId, CreateTicketRequest request) {
        if (canJoin(playsiteId)) {
            Ticket ticket = new Ticket(
                    idGenerator.incrementAndGet(),
                    playsiteId,
                    request.userId(),
                    false);
            tickets.put(ticket.id(), ticket);

            return Mono.just(ticket);
        } else {
            if (request.acceptWaitingInQueue()) {
                Ticket ticket = new Ticket(
                        idGenerator.incrementAndGet(),
                        playsiteId,
                        request.userId(),
                        true);
                tickets.put(ticket.id(), ticket);

                return Mono.just(ticket);
            } else {

            }
        }

        Ticket ticket = new Ticket(
                idGenerator.incrementAndGet(),
                playsiteId,
                request.userId(),
                request.attractionId(),
                request.inQueue());
        tickets.put(ticket.id(), ticket);

        return Mono.just(ticket);
    }

    public Flux<Ticket> findAllForPlaysite(Integer playsiteId) {
        return Flux.fromIterable(tickets.values())
                .filter(ticket -> ticket.playsiteId().equals(playsiteId));
    }
}