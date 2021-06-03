package projet2;

import java.io.IOException;
//import java.util.Iterator;
//import java.util.Set;
//import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import java.text.SimpleDateFormat;
import java.util.Date;  
import java.util.ArrayList;


/**
 * Servlet implementation class Connection
 */
@WebServlet("/UserConnection")
public class UserConnection extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserConnection() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		User foundUser = User.findByLoginPwd(email,password);
	    
	    String htmlResponse = "<!DOCTYPE html><html>";
	    
	    // Check id email/password match 
		if (foundUser != null) {

    		String firstName = foundUser.getPrenom();
    		String familyName = foundUser.getNom();
    		htmlResponse += "<h2>Welcome " + firstName + " " + familyName + " !</h2>";
    		HttpSession session = request.getSession();
    		session.setAttribute("login", email);
    		session.setAttribute("password", password);
    		
    		// If user is admin, print navigation options
    		if (foundUser.getRole().equals("admin"))
    			htmlResponse += "<br>Que souhaitez-vous faire ?<ul><li><a href='/td03/formSQL.html'>Créer un nouveau utilisateur</a></li><li><a href='/td03/AddUser'>Afficher la liste des utilisateurs</a></li></ul>";
    		
    		// Print last visit date
    		Cookie [] listCookies=request.getCookies(); 
    		
    		for(int j = 0; j < listCookies.length; j++) {
    			if(listCookies[j].getName().equals(firstName)) {
    				htmlResponse += "Dernière visite le " + listCookies[j].getValue();
    				break;
    			}
    		}
    		
    		// Save current visit date in a cookie for next time
    		String date = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss").format(new Date());
    		Cookie monCookie = new Cookie(firstName, date);
    		    
    		response.addCookie(monCookie);	    	
	    }
		else    	
	    	htmlResponse += "<h2>Invalid username or password...</h2>";
	    
	    htmlResponse += "</html>";

	    response.getWriter().append(htmlResponse);

	}

}
