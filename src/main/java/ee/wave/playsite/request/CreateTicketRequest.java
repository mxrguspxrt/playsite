package ee.wave.playsite.request;

public record CreateTicketRequest(
    Integer userId, 
    Integer playsiteId, 
    Boolean acceptWaitingInQueue
) {
}