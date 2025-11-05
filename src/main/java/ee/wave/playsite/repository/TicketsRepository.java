package ee.wave.playsite.repository;

import org.springframework.stereotype.Repository;
import ee.wave.playsite.model.Ticket;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class TicketsRepository {

    private static final Map<Integer, Ticket> tickets = new ConcurrentHashMap<>();
    private static final AtomicInteger idGenerator = new AtomicInteger();

    public Mono<Ticket> findById(Integer id) {
        return Mono.justOrEmpty(tickets.get(id));
    }

    public Mono<Ticket> createOne(Integer userId, Integer playsiteId, Boolean inQueue) {
        Ticket newTicket = new Ticket(
                idGenerator.incrementAndGet(),
                userId,
                playsiteId,
                inQueue);
        tickets.put(newTicket.id(), newTicket);
        return Mono.just(newTicket);
    }

    public Flux<Ticket> getAllForPlaysite(Integer playsiteId) {
        return Flux.fromIterable(tickets.values())
                .filter(ticket -> ticket.playsiteId().equals(playsiteId));
    }

    public Flux<Ticket> getActiveForPlaysite(Integer playsiteId) {
        return this.getAllForPlaysite(playsiteId).filter(ticket -> Boolean.FALSE.equals(ticket.inQueue()));
    }

    public Mono<Long> getActiveForPlaysiteCount(Integer playsiteId) {
        return this.getActiveForPlaysite(playsiteId).count();
    }

    public Flux<Ticket> getInQueueForPlaysite(Integer playsiteId) {
        return this.getAllForPlaysite(playsiteId).filter(ticket -> Boolean.TRUE.equals(ticket.inQueue()));
    }
}