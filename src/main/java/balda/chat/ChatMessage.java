package balda.chat;

/**
 * Brought by anatolyd on 09.04.2017.
 */
public class ChatMessage {
    private String message;

    public ChatMessage(String message) {
        this.setMessage(message);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
