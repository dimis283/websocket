package com.example.controller;

import com.example.model.ChatMessage;
import com.example.model.MessageType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // Handle messages sent to /app/chat.sendMessage
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        // Add timestamp to message
        chatMessage.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        return chatMessage;
    }

    // Handle user join events
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());

        chatMessage.setType(MessageType.JOIN);
        chatMessage.setContent(chatMessage.getSender() + " joined the chat!");
        chatMessage.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));

        return chatMessage;
    }

    // Example of sending private messages
    public void sendPrivateMessage(String username, ChatMessage message) {
        messagingTemplate.convertAndSendToUser(username, "/queue/private", message);
    }

    // Example of broadcasting to all users
    public void broadcastMessage(ChatMessage message) {
        messagingTemplate.convertAndSend("/topic/public", message);
    }

    // Regular MVC controller for serving the chat page
    @GetMapping("/chat")
    public String chat() {
        return "chat"; // Returns chat.html template
    }

    @GetMapping("/")
    public String index() {
        return "index"; // Returns index.html template
    }
}