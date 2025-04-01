package Service;
import DAO.MessageDAO;
import Model.Message;
import java.util.List;

public class MessageService {
    private final MessageDAO messageDAO;

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public Message getMessageById(int messageId){
        return messageDAO.getMessageById(messageId);
    }

    public Message addMessage(Message message){
        if(message.getMessage_text()==null || message.getMessage_text().trim().isEmpty())
            return null;


        return messageDAO.addMessage(message);
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public List<Message> getAllMessagesByUserId(int userId){
        return messageDAO.getAllMessagesByUserId(userId);
    }

    public Message updateMessage(int messageId, String newContent){
        if(newContent == null || newContent.trim().isEmpty() || newContent.length() > 255)
            return null;
        
        Message exist = messageDAO.getMessageById(messageId);

        if(exist == null)
            return null;

        boolean success = messageDAO.updateMessage(messageId, newContent);

        if(!success)
            return null;
        
        exist.setMessage_text(newContent);
        return exist;
    }

    public Message deleteMessage(int messageId){
        Message message = messageDAO.getMessageById(messageId);
        boolean success = messageDAO.deleteMessage(messageId);

        if(success)
            return message;
            
        return null;
    }
}
