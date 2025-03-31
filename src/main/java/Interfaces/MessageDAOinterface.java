import java.util.List;

public interface MessageDAOInterface{
    void addMessage(Message message);
    List<Message> getAllMessages();
    List<Message> getAllMessagesByUserId(int userId);
    boolean updateMessage(int messageId, String newContent);
    boolean deleteMessage(int messageId);
}