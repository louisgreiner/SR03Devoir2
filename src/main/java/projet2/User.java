package projet2;
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.InputStream;
//import java.io.OutputStreamWriter;
//import java.net.ServerSocket;
//import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

public class User extends ActiveRecordBase {

	private int id = 0;
	private String nom;
	private String prenom;
	private String email;
	private String password;
	private String role;
	
	// Constructeurs
	public User() {
	}
	
	public User(int id, String nom, String prenom, String email, String password, String role) {
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.password = password;
		this.role = role;
	}
	
	public User(String nom, String prenom, String email, String password, String role) {
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.password = password;
		this.role = role;
	}
	  
	public static void main(String[] args){
	}
	
	// Accesseurs en lecture 
	public int getID() {
		return id;
	}
	
	public String getNom() {
		return nom;
	}
	
	public String getPrenom() {
		return prenom;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getRole(){
		return role;
	}

	// Accesseurs en écriture
	public void setID(int id){
		this.id = id;
	}
	
	public void setNom(String nom){
		this.nom = nom;
	}
	
	public void setPrenom(String prenom){
		this.prenom = prenom;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public void setRole(String role){
		this.role = role;
	}
	
	@Override
    public String toString() { 
        return (prenom + " " + nom + " " + email + " " + password + " " + role); 
    } 
	 
	public boolean equals(User u){
		if (id == u.id)
			return true;
		
		return false;
	}
	
	public boolean authorize(String mail, String pass){
		if (email.equals(mail) && password.equals(pass))
			return true;
		
		return false;
	}
	
	@Override
	protected void _update() { 
        try {
    		Connection db = ConfigConnection.getConnection(Properties.PATH); // pas sûr ici de si ça marche comme ça ou pas
    		Statement sql = db.createStatement();
    		String sqlText = String.format("UPDATE userdb SET nom = '%s', prenom = '%s', email = '%s', pwd = '%s', role = '%s' WHERE id_user = %d",nom, prenom, email, password, role, id);
    		
    		System.out.println("Executing this command: " + sqlText + "\n");
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
    		String sqlText = String.format("INSERT INTO userdb(nom,prenom,email,pwd,role) VALUES ('%s','%s','%s','%s','%s')",nom, prenom, email, password, role);
    		
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
    		String sqlText = String.format("DELETE FROM userdb WHERE id_user=%d", id);
    		
    		System.out.println("Executing this command: " + sqlText + "\n");
    		sql.executeUpdate(sqlText);
        } catch (IOException|SQLException|ClassNotFoundException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }	
	}
	
	public static ArrayList<User> findAll(){
		ArrayList<User> users = new ArrayList<User>();
        try {
    		Connection db = ConfigConnection.getConnection(Properties.PATH);
    		Statement sql = db.createStatement();
    		String sqlText = String.format("SELECT * FROM userdb;");
    		
    		System.out.println("Executing this command: " + sqlText + "\n");
    		ResultSet result = sql.executeQuery(sqlText);
    		
    		while (result.next()) {
    			users.add(new User(
    					result.getInt("id_user"),
    					result.getString("nom"),
    					result.getString("prenom"),
    					result.getString("email"),
    					result.getString("pwd"),
    					result.getString("role"))
    			);
    		}
  
        } catch (IOException|SQLException|ClassNotFoundException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }	
		return users;
	}
	
	public static User findByID(int id2){
    	User wantedUsr = null;

        try {
    		Connection db = ConfigConnection.getConnection(Properties.PATH);
    		Statement sql = db.createStatement();
    		String sqlText = String.format("SELECT * FROM userdb WHERE id_user=%d;",id2);
    		
    		System.out.println("Executing this command: " + sqlText + "\n");
    		ResultSet result = sql.executeQuery(sqlText);
    		
    		if (result.next()) {
    			wantedUsr = new User(result.getInt("id_user"), result.getString("nom"),result.getString("prenom"),result.getString("email"),result.getString("pwd"),result.getString("role"));
    		}
  
        } catch (IOException|SQLException|ClassNotFoundException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }	
		return wantedUsr;
	}
	
	public static User findByLoginPwd(String login, String pwd){
    	User wantedUsr = null;

        try {
    		Connection db = ConfigConnection.getConnection(Properties.PATH);
    														
    		Statement sql = db.createStatement();
    		String sqlText = String.format("SELECT * FROM userdb WHERE (email='%s' AND pwd='%s');",login, pwd);
    		
    		System.out.println("Executing this command: " + sqlText + "\n");
    		ResultSet result = sql.executeQuery(sqlText);
    		
    		if (result.next()) {
    			wantedUsr = new User(result.getInt("id_user"), result.getString("nom"),result.getString("prenom"),result.getString("email"),result.getString("pwd"),result.getString("role"));
        		System.out.println("(findByLoginPwd) WAIT WHAT : " + wantedUsr);

    		}
    		
    		
    		System.out.println("Found : " + wantedUsr);
        } catch (IOException|SQLException|ClassNotFoundException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }	
		return wantedUsr;
	}
	
	public static User findByNames(String nom2, String prenom2){
    	User wantedUsr = null;

        try {
    		Connection db = ConfigConnection.getConnection(Properties.PATH);
    														
    		Statement sql = db.createStatement();
    		String sqlText = String.format("SELECT * FROM userdb WHERE (nom='%s' AND prenom='%s');",nom2, prenom2);
    		
    		System.out.println("Executing this command: " + sqlText + "\n");
    		ResultSet result = sql.executeQuery(sqlText);
    		
    		if (result.next()) {
    			wantedUsr = new User(result.getInt("id_user"), result.getString("nom"),result.getString("prenom"),result.getString("email"),result.getString("pwd"),result.getString("role"));
        		System.out.println("(findByNames) WAIT WHAT : " + wantedUsr);

    		}
        } catch (IOException|SQLException|ClassNotFoundException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
		return wantedUsr;
	}
	
	public static ArrayList<User> findAllInvited(String nom_chat){
		ArrayList<User> users = new ArrayList<User>();

        try {
    		Connection db = ConfigConnection.getConnection(Properties.PATH);
    														
    		Statement sql = db.createStatement();
    		String sqlText = String.format("SELECT * FROM userdb u, invitation i WHERE (i.chat='%s' AND and i.user = u.id_user);",nom_chat);
    		
    		System.out.println("Egxecuting this command: " + sqlText + "\n");
    		ResultSet result = sql.executeQuery(sqlText);
    		
    		while (result.next()) {
    			users.add(new User(
					result.getInt("id_user"),
					result.getString("nom"),
					result.getString("prenom"),
					result.getString("email"),
					result.getString("pwd"),
					result.getString("role"))
    			);
    		}
        } catch (IOException|SQLException|ClassNotFoundException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
		return users;
	}
}




