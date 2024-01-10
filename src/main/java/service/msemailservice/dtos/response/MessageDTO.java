package service.msemailservice.dtos.response;

public record MessageDTO(String from, String to, String subject, String text) {
}
