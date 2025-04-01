package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Service.AccountService;
import Service.MessageService;
import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Account;
import Model.Message;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */

     private final AccountService accountService;
     private final MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService(new AccountDAO());
        this.messageService = new MessageService(new MessageDAO());
    }
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        //Message endpoints
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{id}", this::getMessageByIdHandler);
        app.get("/accounts/{id}/messages", this::getAllMessagesByUserIdHandler);
        app.post("/messages", this::addMessageHandler);
        app.patch("/messages/{id}", this::updateMessageHandler);
        app.delete("/messages/{id}", this::deleteMessageHandler);

        //Accound Endpoints
        app.post("/accounts", this::addAccountHandler);
        app.get("/accounts", this::getAllAccountsHandler);

        //Register and login endpoints
        app.post("/register", this::registerAccountHandler);
        app.post("/login",this::loginHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getAllMessagesHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    private void getMessageByIdHandler(Context ctx){
        int messageId = Integer.parseInt(ctx.pathParam("id"));
        Message msg = messageService.getMessageById(messageId);

        if(msg!=null)
            ctx.json(msg);
        else
            ctx.status(200);
    }

    private void getAllMessagesByUserIdHandler(Context ctx){
        int userId = Integer.parseInt(ctx.pathParam("id"));
        List<Message> messages = messageService.getAllMessagesByUserId(userId);
        ctx.json(messages);
    }

    private void addMessageHandler(Context ctx){
        Message msg = ctx.bodyAsClass(Message.class);
        Message created = messageService.addMessage(msg);

        if(created!=null && created.getMessage_text().length()<=255){
            ctx.status(200).json(created);
        }
        else{
            ctx.status(400);
        }
    }

    private void updateMessageHandler(Context ctx){
        try{
            int messageId = Integer.parseInt(ctx.pathParam("id"));

            Message msgUpdate = ctx.bodyAsClass(Message.class);
            Message updated = messageService.updateMessage(messageId, msgUpdate.getMessage_text());

            if(updated!=null)
                ctx.json(updated);
            else
                ctx.status(400);
        }
        catch (Exception e){
            ctx.status(400);
        }
    }

    private void deleteMessageHandler(Context ctx){
        int messageId = Integer.parseInt(ctx.pathParam("id"));

        Message message = messageService.getMessageById(messageId);

        if(message!=null){
            messageService.deleteMessage(messageId);
            ctx.status(200);
        }
        else
            ctx.status(200);
    }

    private void addAccountHandler(Context ctx){
        try{
            Account act = ctx.bodyAsClass(Account.class);
            if(act.getUsername()==null || act.getUsername().isEmpty() || act.getPassword() == null ||
            act.getPassword().isEmpty()){
                ctx.status(400);
                return;
            }

            Account newAct = accountService.addAccount(act);

            if(newAct!=null)
            ctx.status(201).json(newAct);
        else
            ctx.status(400);
        }
        catch(Exception e){
            ctx.status(400);
        }
    }

    private void getAllAccountsHandler(Context ctx){
        ctx.json(accountService.getAllAccounts());
    }

    private void registerAccountHandler(Context ctx){
        try{
            Account act = ctx.bodyAsClass(Account.class);

            if(act.getUsername() == null || act.getUsername().isBlank() ||
               act.getPassword()==null || act.getPassword().length() < 4){
                    ctx.status(400);
                    return;
               }

            if(accountService.getAccountByUsername(act.getUsername())!=null){
                ctx.status(400);
                return;
            }

            Account newAct = accountService.addAccount(act);

            if(newAct != null)
                ctx.status(200).json(newAct);
            else
                ctx.status(400);
        }
        catch (Exception e){
            ctx.status(400);
        }
    }

    private void loginHandler(Context ctx){
        try{
            Account login = ctx.bodyAsClass(Account.class);
            
            Account found = accountService.getAccountByUsername(login.getUsername());

            if(found != null && found.getPassword().equals(login.getPassword())){
                ctx.status(200).json(found);
            }
            else{
                ctx.status(401);
            }
        }
        catch(Exception e){
            ctx.status(401);
        }
    }
}