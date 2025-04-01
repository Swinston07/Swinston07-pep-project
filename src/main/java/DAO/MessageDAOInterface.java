package DAO;

import Model.Message;

import java.util.List;

public interface MessageDAOInterface{
    Message addMessage(Message message);
    Message getMessageById(int userId);
    List<Message> getAllMessages();
    List<Message> getAllMessagesByUserId(int messageId);
    boolean updateMessage(int messageId, String newContent);
    boolean deleteMessage(int messageId);
}