package projet2;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;
import java.io.PrintWriter;
import java.util.Set;
import java.util.Iterator;

/**
 * Servlet implementation class AddUser
 */
@WebServlet("/AddUser")
public class AddUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Displaying all entries in the db
//        ArrayList<User> users = User.findAll();
		doPost(request,response);
//	    String htmlResponse= "<html>";
//	    String htmlResponse = "<html></h2><br/>Utilisateurs Actuels :<br/><table>";    
//	    User currentUser;
//        
//        for (int i = 0; i < users.size(); i++) {   	        
// 	    	currentUser = users.get(i);
//	    	htmlResponse += "<tr><td>" + currentUser.toString() + "</td></tr>";   
//	    }
//	    htmlResponse += "</table>";
//		
//        htmlResponse += "</html>";
//        response.getWriter().append(htmlResponse);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		// TODO Auto-generated method stub
		String familyName = request.getParameter("User familly name");
		String firstName = request.getParameter("User first name");
		String email = request.getParameter("User email");
		String password = request.getParameter("User password");
		String role = request.getParameter("role");
		
		User newUser = new User(familyName, firstName, email, password, role);

	    // add new entry to hashtable
		newUser.save();

        //Displaying all entries in the db
//        ArrayList<User> users = User.findAll();
	    String htmlResponse = "<html><h2>L'utilisateur " + firstName + " " + familyName +  " a bien été inscrit.</h2>";
//	    User currentUser;
        
//        for (int i = 0; i < users.size(); i++) {   	        
// 	    	currentUser = users.get(i);
//	    	htmlResponse += "<tr><td>" + (i+1) + "/ " + currentUser.toString() + "</td></tr>";   
//	    }
//	    htmlResponse += "</table>";
		htmlResponse += "<form><button type='submit' formaction='formSQL.html'>Retour</button></form>";
        htmlResponse += "</html>";
        response.getWriter().append(htmlResponse);

	}

}
