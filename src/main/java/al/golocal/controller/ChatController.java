package al.golocal.controller;

import al.golocal.entity.Messagesimple;

import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;

import org.springframework.messaging.handler.annotation.SendTo;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ChatController {

    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/message")
    public Messagesimple privateMessage(@RequestBody Messagesimple message){
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(),"/private",message);
        return message;
    }

}