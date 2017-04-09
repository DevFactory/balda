package balda.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.Date;

/**
 * Brought by anatolyd on 09.04.2017.
 */
@Controller
public class ChatController {
    private final Logger log = LoggerFactory.getLogger(ChatController.class);

    private final MessageSendingOperations<String> messagingTemplate;

    public ChatController(MessageSendingOperations<String> messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/user-message")
    @SendTo("/topic/chat")
    public ChatMessage chatMessage(UserMessage userMessage) {
        log.info("Message from user: " + userMessage.getName() + " is [" + userMessage.getMessage() + "]");
        return new ChatMessage(userMessage.getName() + ": " + userMessage.getMessage());
    }

    @Scheduled(cron = "0 */5 * * * *")
    public void robot() {
        UserMessage userMessage = new UserMessage();
        userMessage.setName("heartbeat");
        userMessage.setMessage("I am here at " + new Date());
        log.info("triggered schedule");
        messagingTemplate.convertAndSend("/topic/chat", chatMessage(userMessage));
    }
}

