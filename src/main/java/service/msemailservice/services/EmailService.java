package service.msemailservice.services;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import service.msemailservice.dtos.response.MessageDTO;

@Service
@AllArgsConstructor
public class EmailService {

    private final JavaMailSenderImpl javaMailSender;
    public void sendEmail(MessageDTO messageDTO) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(messageDTO.from());
        message.setTo(messageDTO.to());
        message.setSubject(messageDTO.subject());
        message.setText(messageDTO.text());
        javaMailSender.send(message);
    }
}
