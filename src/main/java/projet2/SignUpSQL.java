package projet2;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet implementation class SignUpSQL
 */
@WebServlet("/SignUpSQL")
public class SignUpSQL extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUpSQL() {
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

		String familyName = request.getParameter("User familly name");
		String firstName = request.getParameter("User first name");
		String email = request.getParameter("User email");
		String password = request.getParameter("User password");
		String role = request.getParameter("role");
		
		User newUser = new User(familyName, firstName, email, password, role);
			    

		User foundUser = User.findByNames(familyName,firstName);
	    	    
	    // Check id email/password match 
		if (foundUser != null) {
		    String htmlResponse = "<!DOCTYPE html><html>";
	    	htmlResponse += "<h2>Warning : this user already exists</h2>";
	    	htmlResponse += newUser.toString();
	    	htmlResponse += "</br>Do you want to continue ?<br>";
	    	htmlResponse += "<form action='http://localhost:8080/SR03-Devoir2/AddUser' method='post'>"
	    	+ "<input type='hidden' name='User familly name' value='" + familyName + "'>"
	    	+ "<input type='hidden' name='User first name' value='" + firstName + "'>"
	    	+ "<input type='hidden' name='User email' value='" + email + "'>"
	    	+ "<input type='hidden' name='User password' value='" + password + "'>"
	    	+ "<input type='hidden' name='role' value='" + role + "'>"
	    	+ "<button type='submit' formaction='formSQL.html'>Cancel</button><button type='submit'>Submit</button></form>";
	    	htmlResponse += "</html>";
	    	response.getWriter().append(htmlResponse);
	    	return;    	
	    }
		
	    
	    // If the user doesn't exist yet, simply forward the request to add him to usersTable
	    RequestDispatcher rd = request.getRequestDispatcher("/AddUser");
	    rd.forward(request,response);
		
	}

}
