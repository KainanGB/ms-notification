package ms.notification.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.notification.dtos.EmailDTO;
import ms.notification.dtos.Message;
import ms.notification.dtos.NewCourseDTO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaEmailListener {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final EmailService emailService;

    @KafkaListener(topics = "MS_AUTHENTICATION_EVENT_SEND_MAIL", containerFactory = "kafkaListenerEmail")
    public void listener(ConsumerRecord<String, Message<EmailDTO>> content) {
        log.warn("reading from queue::MS_AUTHENTICATION_EVENT_SEND_MAIL {}", content);
        emailService.sendEmail(content.value().payload());
    }

    @KafkaListener(topics = "MS_MANAGEMENT_EVENT_NEW_COURSE", containerFactory = "kafkaListenerNewCourse")
    public void listenerManagement(ConsumerRecord<String, Message<NewCourseDTO>> content) {
        log.warn("reading from queue::MS_MANAGEMENT_EVENT_NEW_COURSE {}", content);
    }


}



