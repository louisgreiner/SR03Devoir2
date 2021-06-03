<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="java.io.IOException" %>
<%@ page import="javax.servlet.ServletException" %>
<%@ page import="javax.servlet.annotation.WebServlet" %>
<%@ page import="javax.servlet.http.HttpServlet" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="javax.servlet.http.HttpServletResponse" %>
<%@ page import="javax.servlet.http.Cookie" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="projet2.User" %>
<%@ page import="projet2.Chat" %>
<%@ page import="projet2.Invitation" %>


<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>User Connection</title>
	</head>
	<body>
		<%
		String email;
		String password;
		if (request.getParameter("email") != null) {
			 email = request.getParameter("email");
			 password = request.getParameter("password");
		} else {
			 email = (String)session.getAttribute("login");
			 password = (String)session.getAttribute("password");
		}
		
		User foundUser = User.findByLoginPwd(email, password);
		
		// Check id email/password match
		if(foundUser != null){
			String firstName = foundUser.getPrenom();
			String familyName = foundUser.getNom();
			int id = foundUser.getID();
			
			%><h2>Bienvenue <%= firstName %> !</h2>
			<%
			session.setAttribute("login", email);
			session.setAttribute("password", password);
			
			// If user is admin, print navigation options
			if(foundUser.getRole().equals("admin")){
				%>
				<br>
				<h4>Que souhaitez-vous faire? [ADMIN]</h4>
				<ul>
					<li><a href='/SR03-Devoir2/formSQL.html'>Créer un nouveau utilisateur</a></li>
					<li><a href='/SR03-Devoir2/display.jsp'>Afficher la liste des utilisateurs et des chats</a></li>
					<li><a href='/SR03-Devoir2/createchat.jsp'>Créer un nouveau chat</a></li>
					<li><a href='/SR03-Devoir2/invitechat.jsp'>Inviter des utilisateurs à un chat</a></li>
					<li><a href='/SR03-Devoir2/deleteinvitation.jsp'>Retirer des utilisateurs d'un chat</a></li>
				</ul>
			<%}
			%>
			<h4>Vos Chats: </h4>
			<table>
			<%
			ArrayList<Chat> ownedChats = Chat.chatsOwnedBy(id);
			Iterator<Chat> iter = ownedChats.iterator();
			
			if(iter.hasNext()){%>
			<tr>
				<td>ID / Nom</td>
				<td>Lien</td>
				<td>Description</td>
			</tr>
			<% } else { %>
				<p>Vous n'avez créé aucun chat...</p>
			<% }
			
			// Displaying user's chats
			while(iter.hasNext()){
				Chat currentChat = iter.next();
				%>
				<tr>
					<td><%= currentChat.getNom() %></td>
					<td><a href="http://localhost:8080/SR03-Devoir2/client.html?chat=<%= currentChat.getNom() %>">localhost:8080/SR03-Devoir2/client.html?chat=<%= currentChat.getNom() %></a></td>
					<td><%= currentChat.getDescription() %></td>
				</tr>
				<%
			}
			%>
			</table>
				
			<h4>Vos invitations :</h4>
			<table>
			<%
			ArrayList<Chat> invitedChats = Chat.invitations(id);
			iter = invitedChats.iterator();
			
			if(iter.hasNext()){ %>
			<tr>
				<td>ID / Nom</td>
				<td>Lien</td>
				<td>Description</td>
			</tr>
			<% } else { %>
				<p>Vous n'avez aucune invitation...</p>
			<% }
			
			// Displaying user's chats
			while(iter.hasNext()){ 
				Chat currentChat = iter.next();
				%>
				<tr>
					<td><%= currentChat.getNom() %></td>
					<td><a href="http://localhost:8080/SR03-Devoir2/client.html?chat=<%= currentChat.getNom() %>">localhost:8080/SR03-Devoir2/client.html?chat=<%= currentChat.getNom() %></a></td>
					<td><%= currentChat.getDescription() %></td>
				</tr>
				<%
			}
			%>
			</table>
			
			
			<%
			// Print last visit date
			Cookie [] listCookies = request.getCookies();
			
			for(int j = 0; j < listCookies.length; j++){
				if(listCookies[j].getName().equals(firstName)){
					%><p>Dernière visite le <%= listCookies[j].getValue() %></p><%
					break;
				}
			}
			
			// Save current visit date in a cookie for next time
			String date = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss").format(new Date());
			Cookie monCookie = new Cookie(firstName, date);
			
			response.addCookie(monCookie);
			%>
			<form action="connexion.html">
        		<button type="submit">Déconnexion</button>
    	 	</form>
      		<%
		} else {
			%><h2>Aucun utilisateur n'a été trouvé avec ce login ou mot de passe</h2>
			<form action="connexion.html">
	        	<button type="submit">Retour à l'écran de connexion</button>
	      	</form><%
		}
		%>
	</body>
</html>