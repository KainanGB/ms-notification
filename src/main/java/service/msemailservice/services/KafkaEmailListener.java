package service.msemailservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import service.msemailservice.dtos.response.MessageDTO;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaEmailListener {

    private final EmailService emailService;

    @KafkaListener(topics = "MS_AUTHENTICATION_EVENT_SEND_MAIL")
    public void listener(MessageDTO content) {
        emailService.sendEmail(content);
    }
}



