package org.chat.chatsystem.controller;

import org.chat.chatsystem.Model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.chat.chatsystem.repository.ChatMessageRepository;

import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @GetMapping("/")
    public String index(Model model) {

        List<ChatMessage> messages = chatMessageRepository.findAll();
        model.addAttribute("history", messages);
        return "index";
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(ChatMessage message) {
        chatMessageRepository.save(message);
        return message;
    }
}
