package projet2;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.LocalDate;
import java.time.LocalTime;

public class Chat extends ActiveRecordBase{
	private String nom_chat;
	private int proprietaire;
	private String description;
	private LocalDate date_fin;
	private LocalTime duree_validite;		 
	
	// Constructeurs
	public Chat(String nom_chat, int proprietaire, String description, LocalDate date, LocalTime duree) {
		this.nom_chat = nom_chat;
		this.proprietaire = proprietaire;
		this.description = description;
		this.date_fin = date;
		this.duree_validite = duree; 
	}
	
	// Accesseurs en lecture 
	public String getNom() {
		return nom_chat;
	}

	public int getProprietaire() {
		return proprietaire;
	}
	
	public String getDescription() {
		return description;
	}
	
	public LocalDate getDate() {
		return date_fin;
	}
	
	public LocalTime getDuree() {
		return duree_validite;
	}

	// Accesseurs en écriture
	public void setNom(String nom){
		this.nom_chat = nom;
	}
	public void setProprietaire(int proprietaire){
		this.proprietaire= proprietaire;
	}
	
	public void setDescription(String desc) {
		this.description = desc;
	}
	
	public void setDate(LocalDate date) {
		this.date_fin = date;
	}
	
	public void setDuree(LocalTime duree) {
		this.duree_validite = duree;
	}

	
	@Override
	protected void _update() { 
        try {
    		Connection db = ConfigConnection.getConnection(Properties.PATH);
    		Statement sql = db.createStatement();
    		String sqlText = String.format("UPDATE chat SET nom_chat = '%s', proprietaire= '%d' WHERE nom_chat = %s",nom_chat, proprietaire, nom_chat);
    		
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
    		String sqlText = String.format("INSERT INTO chat(nom_chat,proprietaire, description, date_fin, duree_validite) VALUES ('%s','%d','%s', '%s', '%s')",nom_chat, proprietaire, description, date_fin.toString(), duree_validite.toString());
    		
    		System.out.println("Egxecuting this command: " + sqlText + "\n");
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
    		String sqlText = String.format("DELETE FROM chat WHERE nom_chat=%s", nom_chat);
    		
    		System.out.println("Egxecuting this command: " + sqlText + "\n");
    		sql.executeUpdate(sqlText);
        } catch (IOException|SQLException|ClassNotFoundException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }	
	}
	
	public static Chat findByName(String name){
    	Chat wantedChat = null;

        try {
    		Connection db = ConfigConnection.getConnection(Properties.PATH);
    														
    		Statement sql = db.createStatement();
    		String sqlText = String.format("SELECT * FROM chat WHERE nom_chat='%s';",name);
    		
    		System.out.println("Egxecuting this command: " + sqlText + "\n");
    		ResultSet result = sql.executeQuery(sqlText);
    		
    		if (result.next()) {
    			wantedChat= new Chat(result.getString("nom_chat"), result.getInt("proprietaire"),result.getString("description"), LocalDate.parse(result.getString("date_fin")), LocalTime.parse(result.getString("duree_validite")));
    		}
  
        } catch (IOException|SQLException|ClassNotFoundException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }	
		return wantedChat;
	}
	
	public static ArrayList<Chat> findAll(){
		ArrayList<Chat> chats = new ArrayList<Chat>();
        try {
    		Connection db = ConfigConnection.getConnection(Properties.PATH);
    		Statement sql = db.createStatement();
    		String sqlText = String.format("SELECT * FROM chat;");
    		
    		System.out.println("Executing this command: " + sqlText + "\n");
    		ResultSet result = sql.executeQuery(sqlText);
    		
    		while (result.next()) {
    			chats.add(new Chat(result.getString("nom_chat"), result.getInt("proprietaire"),result.getString("description"), LocalDate.parse(result.getString("date_fin")), LocalTime.parse(result.getString("duree_validite")))
				);
    		}
  
        } catch (IOException|SQLException|ClassNotFoundException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }	
		return chats;
	}
	
	public static ArrayList<Chat> chatsOwnedBy(int idOwner){
		ArrayList<Chat> chats = new ArrayList<Chat>();
        try {
    		Connection db = ConfigConnection.getConnection(Properties.PATH);

    		PreparedStatement sqlQuery = db.prepareStatement("SELECT * FROM chat WHERE proprietaire = ? AND (date_fin > ? OR (date_fin = ? AND duree_validite >  ?));");  
    		
    		sqlQuery.setInt(1, idOwner);
    		sqlQuery.setString(2, LocalDate.now().toString());
    		sqlQuery.setString(3, LocalDate.now().toString());
    		sqlQuery.setString(4, LocalTime.now().toString());
    		
    		System.out.println("Egxecuting this command: " + sqlQuery + "\n");
    		ResultSet result=sqlQuery.executeQuery();
    		
    		while (result.next()) {
    			chats.add(new Chat(result.getString("nom_chat"), result.getInt("proprietaire"),result.getString("description"), LocalDate.parse(result.getString("date_fin")), LocalTime.parse(result.getString("duree_validite")))
    			);
    		}
  
        } catch (IOException|SQLException|ClassNotFoundException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }	
		return chats;
	}
	
	public static ArrayList<Chat> invitations(int idUser){
		ArrayList<Chat> chats = new ArrayList<Chat>();
        try {
    		Connection db = ConfigConnection.getConnection(Properties.PATH);

    		PreparedStatement sqlQuery = db.prepareStatement("SELECT * FROM invitation i, chat c WHERE i.user = ? and i.chat = c.nom_chat AND (c.date_fin > ? OR (c.date_fin = ? AND c.duree_validite >  ?)) ");  
    		sqlQuery.setInt(1, idUser);
    		sqlQuery.setString(2, LocalDate.now().toString());
    		sqlQuery.setString(3, LocalDate.now().toString());
    		sqlQuery.setString(4, LocalTime.now().toString());
    		
    		System.out.println("Egxecuting this command: " + sqlQuery + "\n");
    		ResultSet result=sqlQuery.executeQuery();
    		
    		while (result.next()) {
    			chats.add(new Chat(result.getString("nom_chat"), result.getInt("proprietaire"),result.getString("description"), LocalDate.parse(result.getString("date_fin")), LocalTime.parse(result.getString("duree_validite")))
    			);
    			
    		//	System.out.println("TROUVÉ : " + " " + result.getInt("id_chat") + " " + result.getInt("proprietaire") + " " + result.getString("lien"));

    		}
    		System.out.println("\nFIN ITER\n");

  
        } catch (IOException|SQLException|ClassNotFoundException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }	
		return chats;
	}
}