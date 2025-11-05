package ee.wave.playsite.model;

public record Ticket(Integer id, Integer playsiteId, Integer userId, Boolean inQueue) {
}