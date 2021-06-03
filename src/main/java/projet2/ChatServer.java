package projet2;
import java.util.Hashtable;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;
import org.json.*;
import java.util.ArrayList;


@ServerEndpoint(value="/{chat}/{pseudo}", 
                configurator=ChatServer.EndpointConfigurator.class)

public class ChatServer {

	    
    private static ChatServer singleton = new ChatServer();

    private ChatServer() {
    }

    /**
     * Acquisition de notre unique instance ChatServer
     */
    public static ChatServer getInstance() {
        return ChatServer.singleton;
    }

    /**
     * On maintient toutes les sessions utilisateurs dans une collection.
     */
    private Hashtable<String, Session> sessions = new Hashtable<>();
    
    /**
     * Cette m»thode est d»clench»e á chaque connexion d'un utilisateur.
     */
    @OnOpen
    public void open(Session session, @PathParam("pseudo") String pseudo, @PathParam("chat") String chat) {
        sendMessage( "Admin >>> Connection established for " + pseudo + " in chat " + chat, chat);
        session.getUserProperties().put( "chat/pseudo", chat + "/" + pseudo);
        sessions.put(session.getId(), session);
        sendUserList(chat);
    }

    /**
     * Cette m»thode est d»clench»e á chaque d»connexion d'un utilisateur.
     */
    @OnClose
    public void close(Session session) {
    	String[] chat_pseudos_map = ((String) session.getUserProperties().get( "chat/pseudo" )).split("/");
        String chat =  chat_pseudos_map[0];
        String pseudo =  chat_pseudos_map[1];
        sessions.remove( session.getId() );
        sendMessage( "Admin >>> Connection closed for " + pseudo + " in chat " + chat, chat);
        sendUserList(chat);
    }

    /**
     * Cette m»thode est d»clench»e en cas d'erreur de communication.
     */
    @OnError
    public void onError(Throwable error) {
        System.out.println( "Error: " + error.getMessage() );
    }

    /**
     * Cette m»thode est d»clench»e á chaque r»ception d'un message utilisateur.
     */
    @OnMessage
    public void handleMessage(String message, Session session) {
    	String[] chat_pseudos_map = ((String) session.getUserProperties().get( "chat/pseudo" )).split("/");
        String chat =  chat_pseudos_map[0];
        String pseudo =  chat_pseudos_map[1];
        String fullMessage = pseudo + " >>> " + message; 
        
        sendMessage(fullMessage, chat);
    }

    /**
     * Une m»thode priv»e, sp»cifique á notre exemple.
     * Elle permet l'envoie d'un message aux participants de la discussion.
     */
    private void sendMessage( String fullMessage, String chat) {
        // Affichage sur la console du server Web.
        System.out.println( fullMessage );      
     	JSONObject json = new JSONObject();
     	json.put("messages", fullMessage);
     	
        // On envoie le message á tout le monde.
        for( Session session : sessions.values() ) {
        	String[] chat_pseudos_map = ((String) session.getUserProperties().get( "chat/pseudo" )).split("/");
            String currentChat =  chat_pseudos_map[0];
            String currentPseudo =  chat_pseudos_map[1];
        	if (chat.equals(currentChat)) {
                try {
                    session.getBasicRemote().sendText( json.toString() );
                } catch( Exception exception ) {
                    System.out.println( "ERROR: cannot send message to " + session.getId() );
                }
        	}
        }       
    }
   
    
    private String CreateUserJsonData(String chat) {
    	
    	ArrayList<String> users = new ArrayList<String>();
     	JSONObject json = new JSONObject();
     	for(Session session : sessions.values() ) {
        	String[] chat_pseudos_map = ((String) session.getUserProperties().get( "chat/pseudo" )).split("/");
            String currentChat =  chat_pseudos_map[0];
            String currentPseudo =  chat_pseudos_map[1];
            if(chat.equals(currentChat)) {
         		users.add(currentPseudo);
            }
     	}
     	json.put("users", users);
     	return json.toString();
    }
    
    private void sendUserList(String chat) {
    	for(Session session : sessions.values() ) {
        	String[] chat_pseudos_map = ((String) session.getUserProperties().get( "chat/pseudo" )).split("/");
            String currentChat =  chat_pseudos_map[0];
            String currentPseudo =  chat_pseudos_map[1];
            
        	if (chat.equals(currentChat)) {
                try {
                    session.getBasicRemote().sendText(CreateUserJsonData(chat));
                } catch( Exception exception ) {
                    System.out.println( "ERROR: cannot send message to " + session.getId() );
                }
        	}

    	} 
	}
    
    
    
    /**
     * Permet de ne pas avoir une instance diff»rente par client.
     * ChatServer est donc g»rer en "singleton" et le configurateur utilise ce singleton. 
     */
    public static class EndpointConfigurator extends ServerEndpointConfig.Configurator {
        @Override 
        @SuppressWarnings("unchecked")
        public <T> T getEndpointInstance(Class<T> endpointClass) {
            return (T) ChatServer.getInstance();
        }
    }
}
