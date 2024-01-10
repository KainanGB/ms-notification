package service.msemailservice.mq;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import service.msemailservice.dtos.response.MessageDTO;
import service.msemailservice.services.EmailService;

import java.util.Objects;

@Component
@AllArgsConstructor
public class RabbitListenerMQ {

    private final EmailService emailService;

    @RabbitListener(queues = "authentication.email.queue")
    public void readMessage(@Payload MessageDTO payload) {

        if (Objects.equals(payload.from(), "kainanytbr@gmail.com")) {
            throw new RuntimeException("DEU RUIM AQUI");
        }

        System.out.println("foi lida aqui" + payload);
        emailService.sendEmail(payload);
    }


}
