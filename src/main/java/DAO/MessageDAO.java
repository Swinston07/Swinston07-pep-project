package DAO;

import java.util.List;
import java.util.ArrayList;
import java.sql.*;
import Util.ConnectionUtil;
import Model.Message;

public class MessageDAO implements MessageDAOInterface{
    @Override
    public Message addMessage(Message message){
        String sql = "INSERT INTO message(posted_by, message_text, time_posted_epoch) VALUES(?,?,?)";

        if(message.getMessage_text()==null || message.getMessage_text().trim().isEmpty())
            return null;
            
        try(Connection conn = ConnectionUtil.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());

            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();

            if(rs.next()){
                int messageId = rs.getInt(1);
                return new Message(messageId, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Message getMessageById(int messageId){
        String sql = "SELECT * FROM message WHERE message_id = ?";
        
        try(Connection conn = ConnectionUtil.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, messageId);
            
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
            }
        }
        catch (Exception e){
            e.printStackTrace();;
        }
        return null;
    }

    @Override
    public List<Message> getAllMessages(){
        String sql = "SELECT * FROM message";
        List<Message> messages = new ArrayList<>();

        try(Connection conn = ConnectionUtil.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                messages.add(
                    new Message(
                        rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch")
                    )
                );
            }
            return messages;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Message> getAllMessagesByUserId(int userId){
        String sql = "SELECT * FROM message WHERE posted_by = ?";
        List<Message> messages = new ArrayList<>();

        try(Connection conn = ConnectionUtil.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                messages.add(
                    new Message(
                        rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch")
                    )
                );
            }
            return messages;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateMessage(int messageId, String newContent){
        String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";

        try(Connection conn = ConnectionUtil.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, newContent);
            ps.setInt(2, messageId);
            int affectedRows = ps.executeUpdate();

            return affectedRows>0;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteMessage(int messageId){
        String sql = "DELETE FROM message WHERE message_id = ?";

        try(Connection conn = ConnectionUtil.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, messageId);

            int affectedRows = ps.executeUpdate();

            return affectedRows>0;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}