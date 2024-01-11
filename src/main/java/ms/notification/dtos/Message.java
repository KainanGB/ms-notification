package ms.notification.dtos;

public record Message<T>(String correlationId, T payload) {

}
