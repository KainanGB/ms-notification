package service.msemailservice.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.msemailservice.dtos.response.MessageDTO;
import service.msemailservice.services.EmailService;

@RestController
@AllArgsConstructor
@RequestMapping("/mail/confirm")
public class SendMailController {

    private final EmailService emailService;

    @PostMapping
    public void sendMessage(@RequestBody MessageDTO data) {
        final MessageDTO message = new MessageDTO("kainanytbr@gmail.com", data.to(), data.subject(), data.text());
        emailService.sendEmail(message);
    }
}


