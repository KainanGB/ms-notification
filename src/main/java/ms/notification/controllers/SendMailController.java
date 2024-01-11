package ms.notification.controllers;

import lombok.AllArgsConstructor;
import ms.notification.dtos.EmailDTO;
import ms.notification.services.EmailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/mail/confirm")
public class SendMailController {

    private final EmailService emailService;

    @PostMapping
    public void sendMessage(@RequestBody EmailDTO data) {
        final EmailDTO message = new EmailDTO("kainanytbr@gmail.com", data.to(), data.subject(), data.text());
        emailService.sendEmail(message);
    }
}


