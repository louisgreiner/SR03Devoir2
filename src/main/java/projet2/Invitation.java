package projet2;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Invitation extends ActiveRecordBase {

	private String chat;
	private int user;
	
	// Constructeurs
	public Invitation() {
	}
	
	public Invitation(String chat, int user) {
		this.chat = chat;
		this.user = user;
	}
	  
	public static void main(String[] args){
	}
	
	// Accesseurs en lecture 
	public String getChat() {
		return chat;
	}
	
	public int getUser() {
		return user;
	}

	// Accesseurs en écriture
	public void setChat(String chat){
		this.chat = chat;
	}
	
	public void setUser(int user){
		this.user = user;
	}
	
//	@Override
//    public String toString() { 
//        return (prenom + " " + nom + " " + email + " " + password + " " + role); 
//    } 
//	 
//	public boolean equals(User u){
//		if (id == u.id)
//			return true;
//		
//		return false;
//	}
//	
//	public boolean authorize(String mail, String pass){
//		if (email.equals(mail) && password.equals(pass))
//			return true;
//		
//		return false;
//	}
	
	@Override
	protected void _update() { 
        try {
    		Connection db = ConfigConnection.getConnection(Properties.PATH);
    		Statement sql = db.createStatement();
    		String sqlText = String.format("UPDATE invitation SET chat = '%s', user = '%d' WHERE chat=%s AND user=%d",chat, user, chat, user);
    		
    		System.out.println("Egxecuting this command: " + sqlText + "\n");
    		sql.executeUpdate(sqlText);
        } catch (IOException|SQLException|ClassNotFoundException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }	
	}
	
	@Override
	protected void _insert() { 
        try {
    		Connection db = ConfigConnection.getConnection(Properties.PATH);
    		Statement sql = db.createStatement();
    		String sqlText = String.format("INSERT INTO invitation(chat, user) VALUES ('%s','%d')", chat, user);
    		
    		System.out.println("Executing this command: " + sqlText + "\n");
    		sql.executeUpdate(sqlText);
        } catch (IOException|SQLException|ClassNotFoundException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }	
	}
	
	@Override
	protected void _delete() { 
        try {
    		Connection db = ConfigConnection.getConnection(Properties.PATH);
    		Statement sql = db.createStatement();
    		String sqlText = String.format("DELETE FROM invitation WHERE chat='%s' AND user='%d'", chat, user);
    		
    		System.out.println("Executing this command: " + sqlText + "\n");
    		sql.executeUpdate(sqlText);
        } catch (IOException|SQLException|ClassNotFoundException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }	
	}
	
	public static Invitation findByChatUser(String chat, int user){
		Invitation wantedInvitation = null;

        try {
    		Connection db = ConfigConnection.getConnection(Properties.PATH);
    														
    		Statement sql = db.createStatement();
    		String sqlText = String.format("SELECT * FROM invitation WHERE (chat='%s' AND user='%d');",chat, user);
    		
    		System.out.println("Egxecuting this command: " + sqlText + "\n");
    		ResultSet result = sql.executeQuery(sqlText);
    		
    		if (result.next()) {
    			wantedInvitation = new Invitation(result.getString("chat"), result.getInt("user"));
    		}
    		
    		
        } catch (IOException|SQLException|ClassNotFoundException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }	
		return wantedInvitation;
	}
	
}